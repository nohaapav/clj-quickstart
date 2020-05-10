(ns web.router
  (:require
    [clojure.core.async :refer [put! <! >! chan go-loop]]
    [reitit.frontend :as rf]
    [reitit.frontend.easy :as rfe]
    [reitit.coercion.spec :as rss]
    [web.routes :as routes]))

(def ^:private route-chan (chan 2))

(defn url
  ([route]
   (url route {} {}))
  ([route params]
   (rfe/href route params))
  ([route params query]
   (rfe/href route params query)))

(defn navigate-to!
  ([route]
   (navigate-to! route nil nil nil))
  ([route params]
   (navigate-to! route params nil nil))
  ([route params query]
   (navigate-to! route params query nil))
  ([route params query
    {:keys [pre-navigate post-navigate]
     :or   {pre-navigate  identity
            post-navigate #(.scrollTo js/window 0 0)}
     :as   opts}]
   (pre-navigate)
   (rfe/push-state route params query)
   (post-navigate)))

(defn handler
  ([route]
   (handler route nil nil))
  ([route params]
   (handler route params nil))
  ([route params query]
   (handler route params query nil))
  ([route params query opts]
   (fn [e]
     (.preventDefault e)
     (navigate-to! route params query opts))))

(defn handle-route-change [route-out-ch]
  (go-loop []
           (let [m (<! route-chan)
                 new-route (get-in m [:data :name])
                 new-route-params (get m :path-params)
                 new-query-params (get m :query-params)
                 new-route-data (dissoc (get m :data) :name :parameters :coercion)]
             (>! route-out-ch
                 {:app/route        new-route
                  :app/route-data   new-route-data
                  :app/route-params new-route-params
                  :app/query-params new-query-params})
             (recur))))

(defn init! [route-ch]
  (handle-route-change route-ch)
  (rfe/start!
    (rf/router routes/frontend {:data {:coercion rss/coercion}})
    #(put! route-chan %)
    {:use-fragment false}))