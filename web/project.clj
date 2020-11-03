(defproject web "1.0"
  :description "Clojurescript web template with reitit routing & rum"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.597"]
                 [cljsjs/react "16.8.6-0"]
                 [cljsjs/react-dom "16.8.6-0"]
                 [metosin/reitit "0.3.10"]
                 [rum "0.11.4"]]
  :min-lein-version "2.8.2"
  :plugins [[lein-cljsbuild "1.1.7"]]
  :source-paths ["src"]
  :clean-targets ^{:protect false} ["resources/public/js/out"
                                    "resources/public/js/app.js"
                                    :target-path]
  :repl-options {:init-ns repl.user}
  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src"]

                ;; The presence of a :figwheel configuration here
                ;; will cause figwheel to inject the figwheel client
                ;; into your build
                :figwheel     true
                :compiler     {:main                 web.app
                               :asset-path           "js/out"
                               :output-to            "resources/public/js/app.js"
                               :output-dir           "resources/public/js/out"
                               :infer-externs        true
                               :parallel-build       true
                               :source-map-timestamp true

                               ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                               ;; https://github.com/binaryage/cljs-devtools
                               :preloads             [devtools.preload]}}

               ;; This next build is a compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id           "min"
                :source-paths ["src"]
                :compiler     {:main                 web.app
                               :output-to            "resources/public/js/app.js"
                               :output-dir           "target"
                               :parallel-build       true
                               :infer-externs        true
                               :source-map-timestamp true
                               :optimizations        :advanced
                               :closure-defines      {"goog.DEBUG" false}
                               :pretty-print         false
                               :pseudo-names         false}}]}
  :figwheel {:css-dirs ["resources/public/css"]}

  ;; Setting up nREPL for Figwheel and ClojureScript dev
  ;; Please see:
  ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
  :profiles {:dev  {:dependencies [[figwheel "0.5.17"]
                                   [figwheel-sidecar "0.5.17"]
                                   [binaryage/devtools "0.9.10"]
                                   [cider/piggieback "0.4.1"]]
                    :plugins      [[lein-figwheel "0.5.17"]
                                   [lein-doo "0.1.6"]]
                    :source-paths ["src" "dev"]
                    :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}
             :prod {:prep-tasks  ["cljsbuild" "once" "min"]
                    :omit-source true
                    :aot         :all}})