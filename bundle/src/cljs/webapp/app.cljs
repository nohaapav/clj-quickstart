(ns webapp.app
  (:require [cljs.core.async :as async :refer [<! go-loop]]
            [goog.dom :refer [getElement]]
            [reagent.dom :as re-dom]
            [reagent.core :as re-core]
            [webapp.router :as router]
            [webapp.db :as db]
            [webapp.component.root :refer [Root]]
            [webapp.events]
            [webapp.subs]))

(enable-console-print!)

(def route->component
  {:index Root})

(defn render-route [root-el {:keys [app/route app/route-params app/query-params]}]
  (let [Comp (get route->component route)]
    (re-dom/render [Comp] root-el)))

(defn handle-route-change [root-el route-ch]
  (go-loop []
           (let [route (<! route-ch)]
             (swap! db/default merge route)
             (render-route root-el route)
             (recur))))

(defn init []
  (let [route-ch (async/chan)
        root-el (getElement "app")]
    (handle-route-change root-el route-ch)
    (router/init! route-ch)))

(init)