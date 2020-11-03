(ns app.handler
  (:require [app.version :as version]))

;; Index handler

(defn index
  [{{:keys [query path body]} :parameters}]
  {:status 200
   :body   {:info (version/info)}})