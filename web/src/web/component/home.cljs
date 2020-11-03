(ns web.component.home
  (:require [rum.core :as rum]))

(rum/defc Home < rum/static
  [props]
  [:div "It works"])
