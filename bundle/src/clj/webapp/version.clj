(ns webapp.version
  (:require [clojure.java.io :as io]
            [clojure.walk :refer [keywordize-keys]])
  (:import (java.util Properties)))

(def pom-properties
  (doto (Properties.)
    (.load (-> "META-INF/maven/webapp/webapp/pom.properties"
               (io/resource)
               (io/reader)))))

(defn info
  []
  {:version  (get pom-properties "version")
   :revision (get pom-properties "revision")})