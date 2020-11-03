(ns repl.user
  (:require [app.server :as server]
            [ring.adapter.jetty :as jetty]))

;; Let Clojure warn you when it needs to reflect on types, or when it does math
;; on unboxed numbers. In both cases you should add type annotations to prevent
;; degraded performance.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(defn start-ring-server []
  (jetty/run-jetty server/app {:port 3000, :join? false}))