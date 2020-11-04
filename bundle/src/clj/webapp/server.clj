(ns webapp.server
  (:gen-class)
  (:require [clojure.tools.logging :as log]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [org.httpkit.server :refer [run-server]]
            [reitit.core :as r]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.dev.pretty :as pretty]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.ring.middleware.multipart :as multipart]
            [muuntaja.core :as m]
            [webapp.routes :as routes]
            [webapp.handler :as handler]))

(defn backend-fe-handler
  [req]
  "Check whether GET request and uri match known FE route.
   TRUE  => Redirect to index so FE router handle routing instead of BE ring router
   FALSE => NOT-FOUNT 404"
  (if (and (= :get (:request-method req))
           (r/match-by-path routes/backend-fe-router (:uri req)))
    (handler/index req)
    (handler/resp-not-found)))

(def app
  (ring/ring-handler
    (ring/router
      routes/backend
      {;;:reitit.middleware/transform dev/print-request-diffs ;; pretty diffs
       ;;:validate spec/validate ;; enable spec validation for route data
       ;;:reitit.spec/wrap spell/closed ;; strict top-level validation
       :exception pretty/exception
       :data      {:coercion   reitit.coercion.spec/coercion
                   :muuntaja   m/instance
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
      (-> (ring/create-default-handler
            {:not-found backend-fe-handler})
          (wrap-resource "public")
          (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
          wrap-gzip))))

(defn -main [& [port]]
  (log/info "App is starting...")
  (let [port (or port 8080)]
    (run-server app {:port port} :thread 8 :queue-size 300000)
    (log/info "App is running on port" port)))