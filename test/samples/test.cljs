(ns samples.test
  (:require
   [i18n.core :as i18n]))

(defn app []
  [:div.container
   [:h3
    (i18n/t :components.app/hi-mom "Hi mom!")]
   [:div
    [:section
     [:p
      [:span
       (i18n/t :another.place/foo "foo")]]]]])
