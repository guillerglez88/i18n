(ns samples.test
  (:require
   [i18n.core :as i18n]))

(i18n/t :test.clj/hi-mom "Hi mom!")

(defn translation-inside-fn []
  (let [foo "foo"]
    (if foo
      (i18n/t :inside.fn/foo "foo")
      (i18n/t :bar "bar"))))
