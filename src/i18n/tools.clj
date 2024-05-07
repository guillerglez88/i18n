(ns i18n.tools
  (:require
   [clojure.tools.reader :as reader]
   [clojure.tools.analyzer.jvm :as ana.jvm]
   [clojure.tools.analyzer.ast :as ana.ast]
   [clojure.java.io :as io]
   [i18n.core :as i18n]))

(defn extract [f]
  (letfn [(read-file-forms [^java.io.Reader rdr]
            (let [next (fn [rdr]
                         (try
                           (reader/read (java.io.PushbackReader. rdr))
                           (catch clojure.lang.ExceptionInfo _exc
                             nil)))]
              (when-let [form (next rdr)]
                (cons form (lazy-seq (read-file-forms rdr))))))
          (translation [{:keys [op fn args]}]
            (when (and (= :invoke op)
                       (#{#'i18n/t :i18n/t} (:var fn)))
              (mapv :val args)))
          (unresolved-sym [ns name & _args]
            {:var (keyword (str ns) (str name))})]
    (with-open [rdr (io/reader f)]
      (->> (read-file-forms rdr)
           (map #(ana.jvm/analyze % (ana.jvm/empty-env) {:passes-opts {:validate/unresolvable-symbol-handler unresolved-sym}}))
           (mapcat ana.ast/nodes)
           (map translation)
           (remove nil?)
           (into {})))))
