(ns web.app
  (:require [cljs.core.async :as async :refer [<! go-loop]]
            [goog.dom :refer [getElement]]
            [web.router :as router]
            [web.component.home :refer [Home]]
            [rum.core :as rum]))

(enable-console-print!)

(defonce app-state (atom {}))

(def route->component
  {:index Home})

(defn render-route [root-el {:keys [app/route app/route-params app/query-params]}]
  (let [Comp (get route->component route)
        props {:route        route
               :route-params route-params
               :query-params query-params}]
    (rum/mount (Comp props) root-el)))

(defn handle-route-change [root-el route-ch]
  (go-loop []
           (let [route (<! route-ch)]
             (swap! app-state merge route)
             (render-route root-el route)
             (recur))))

(defn init []
  (let [route-ch (async/chan)
        root-el (getElement "app")]
    (handle-route-change root-el route-ch)
    (router/init! route-ch)))

(init)


