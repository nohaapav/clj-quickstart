(ns webapp.component.root
  (:require [re-frame.core :refer [subscribe dispatch]]
            [reagent.core :as re-core]))

(defn did-mount [comp]
  (dispatch [:get-version]))

(defn render []
  (let [version @(subscribe [:version])]
    [:span (str "Running version: " version)]))

(defn Root []
  (re-core/create-class
    {:display-name        "Root"
     :component-did-mount did-mount
     :reagent-render      render}))