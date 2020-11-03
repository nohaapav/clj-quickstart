(ns app.server
  (:gen-class)
  (:require [ring.middleware.json :refer [wrap-json-params wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [org.httpkit.server :refer [run-server]]
            [clojure.tools.logging :as log]
            [clojure.tools.nrepl.server :as nrepl]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.dev.pretty :as pretty]
            [reitit.ring.spec :as rrs]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.spec :as rs]
            [expound.alpha :as e]
            [muuntaja.core :as m]
            [app.routes :as routes]))

(def app-middleware
  [;; negotiation, request decoding and response encoding
   muuntaja/format-middleware
   ;; query-params & form-params
   parameters/parameters-middleware
   ;; exception handling
   exception/default-handlers
   ;; coercing response body (disabled)
   coercion/coerce-response-middleware
   ;; coercing request parameters
   coercion/coerce-request-middleware])

(def app
  (ring/ring-handler
    (ring/router
      routes/api
      {:exception   pretty/exception
       :validate    rrs/validate
       ::rs/explain e/expound-str
       :conflicts   nil
       :data        {:coercion   reitit.coercion.spec/coercion
                     :muuntaja   m/instance
                     :middleware app-middleware}})
    (-> (ring/create-default-handler)
        (wrap-defaults site-defaults))))