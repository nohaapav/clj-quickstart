(ns repl.user
  (:require [figwheel.main.api :as fig]
            [ring.middleware.reload :refer [wrap-reload]]
            [webapp.server :refer [app]]))

(def http-handler (wrap-reload app))

(defn cljs-repl
  "Launch a ClojureScript REPL that is connected to your build and host environment."
  []
  (fig/start "dev"))