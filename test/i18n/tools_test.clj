(ns i18n.tools-test
  (:require
   [i18n.tools :as sut]
   [clojure.test :as t]))

(t/deftest extract-test
  (t/testing "Extract lang map from .clj file"
    (t/is (= {:test.clj/hi-mom "Hi mom!"
              :inside.fn/foo "foo"
              :bar "bar"}
             (sut/extract "test/samples/test.clj"))))
  (t/testing "Extract lang map from .cljs file"
    (t/is (= {:components.app/hi-mom "Hi mom!"
              :another.place/foo "foo"}
             (sut/extract "test/samples/test.cljs")))))
