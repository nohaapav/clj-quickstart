(ns repl.user
  (:require [figwheel.main.api :as fig]))

(defn cljs-repl
  "Launch a ClojureScript REPL that is connected to your build and host environment."
  []
  (fig/start "dev"))