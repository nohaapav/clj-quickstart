(ns app.server
  (:gen-class)
  (:require [clojure.tools.logging :as log]
            [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.dev.pretty :as pretty]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.ring.middleware.multipart :as multipart]
            [muuntaja.core :as m]
            [app.routes :as routes]))

(def app
  (ring/ring-handler
    (ring/router
      routes/api
      {;;:reitit.middleware/transform dev/print-request-diffs ;; pretty diffs
       ;;:validate spec/validate ;; enable spec validation for route data
       ;;:reitit.spec/wrap spell/closed ;; strict top-level validation
       :exception pretty/exception
       :data {:coercion reitit.coercion.spec/coercion
              :muuntaja m/instance
              :middleware [;; query-params & form-params
                           parameters/parameters-middleware
                           ;; content-negotiation
                           muuntaja/format-negotiate-middleware
                           ;; encoding response body
                           muuntaja/format-response-middleware
                           ;; exception handling
                           exception/exception-middleware
                           ;; decoding request body
                           muuntaja/format-request-middleware
                           ;; coercing response bodys
                           coercion/coerce-response-middleware
                           ;; coercing request parameters
                           coercion/coerce-request-middleware
                           ;; multipart
                           multipart/multipart-middleware]}})
    (ring/routes
      (ring/create-default-handler))))

(defn -main [& [port]]
  (log/info "App is starting...")
  (let [port (or port 8080)]
    (run-server app {:port port} :thread 8 :queue-size 300000)
    (log/info "App is running on port" port)))