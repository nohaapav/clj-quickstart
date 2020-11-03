(ns repl.user
  (:require [clojure.tools.nrepl.server :as nrepl]))

;; Let Clojure warn you when it needs to reflect on types, or when it does math
;; on unboxed numbers. In both cases you should add type annotations to prevent
;; degraded performance.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(defn nrepl-start
  "This start web server for the app on port 7888."
  []
  (nrepl/start-server :port 7888))