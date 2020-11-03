(ns app.routes
  (:require [app.handler :as handler]))

(def api
  [["/"
    {:name :index
     :get  (array-map
             :summary "Index"
             :handler handler/index)}]])