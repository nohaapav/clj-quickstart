(ns webapp.handler
  (:require [net.cgrand.enlive-html :as html :refer [deftemplate]]
            [clojure.tools.logging :as log]
            [clojure.string :as str]
            [webapp.version :as version]))

(defn include-meta [meta]
  (let [name (first meta)
        content (second meta)]
    (first (html/html [:meta {:name    name
                              :content (if (vector? content)
                                         (str/join "," content)
                                         content)}]))))

(defn include-css [href version]
  (first (html/html [:link {:href (str href "?v=" version)
                            :rel  "stylesheet"}])))

(defn include-js [src version]
  (first (html/html [:script {:src (str src "?v=" version)}])))

(deftemplate index-page "index.html"
             [version]
             [:head] (html/append
                       (map #(include-css % version) ["/css/app.css"]))
             [:body] (html/append (map #(include-js % version) ["/js/webapp.js"])))

;; Response

(defn resp-error
  [status response]
  {:status status
   :body   {:error response}})

(defn resp-unauthorized
  [response]
  {:status 401
   :body   {:error response}})

(defn resp-not-found
  []
  {:status  404
   :body    ""
   :headers {}})

(defn resp-ok
  ([] {:status 200})
  ([response] {:status 200
               :body   response}))

;; Index handler

(defn index
  [_]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (apply str (index-page (:version (version/info))))})

;; API handlers

(defn version
  [{{:keys [query path body]} :parameters}]
  (-> (version/info)
      (resp-ok)))