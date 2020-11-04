(ns webapp.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [ajax.core :refer [json-request-format json-response-format]]
            [day8.re-frame.http-fx]
            [webapp.routes :as routes]))

;; -- GET Version @ /api/version --------------------------------------------
;;
(reg-event-fx
  :get-version
  (fn [{:keys [db]} [_ params]]
    {:http-xhrio {:method          :get
                  :uri             (routes/path-for-backend :version)
                  :response-format (json-response-format {:keywords? true})
                  :on-success      [:get-version-success]}}))

(reg-event-db
  :get-version-success
  (fn [db [_ {version :version}]]
    (-> db
        (assoc-in [:version] version))))