(ns webapp.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :version
  (fn [db _]
    (:version db)))