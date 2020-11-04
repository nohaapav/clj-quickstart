(ns webapp.routes
  (:require [reitit.core :as r]
            [reitit.coercion.spec :as rss]
            [clojure.string :as str]
            #?(:cljs [reitit.frontend :as rf])
            #?(:clj [webapp.handler :as handler])))

(def backend
  [["/"
    {:name :index
     :get  (array-map
             :summary "Index"
             #?@(:clj [:handler handler/index]))}]
   ["/api"
    ["/version"
     {:name :version
      :get  (array-map
              :summary "Version"
              #?@(:clj [:handler handler/version]))}]]])

(def frontend
  [["/" :index]])

(def backend-router (r/router backend {:data {:coercion rss/coercion}}))
(def backend-fe-router (r/router frontend))

#?(:cljs
   (def frontend-router (rf/router frontend {:data {:coercion rss/coercion}})))

#?(:cljs
   (defn query-encode [query]
     "If query param coll value, separate by comma"
     (->> query
          (map
            (fn [[key val]]
              [key (if (coll? val)
                     (str/join "," val)
                     val)]))
          (into {}))))

#?(:cljs
   (defn path-for-backend
     ([handler]
      (path-for-backend handler nil nil))
     ([handler params]
      (path-for-backend handler params nil))
     ([handler params query]
      (str/replace
        (-> backend-router
            (r/match-by-name handler params)
            (r/match->path (query-encode query))) #"^/" ""))))

#?(:cljs
   (defn path-for-frontend
     ([handler]
      (path-for-frontend handler nil nil))
     ([handler params]
      (path-for-frontend handler params nil))
     ([handler params query]
      (-> frontend-router
          (rf/match-by-name handler params)
          (r/match->path (query-encode query))))))