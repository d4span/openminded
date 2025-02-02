(ns openminded.test-helper
  (:require [clojure.test :refer :all]
            [clojure.spec.test.alpha :as stest]
            [clojure.tools.namespace.find :as ns-find]
            [clojure.java.classpath :as cp]))

(defn instrument-namespaces-with-prefix [prefix]
  (let [namespaces (ns-find/find-namespaces (cp/classpath))
        openminded-ns (->> namespaces (map name) (filter #(re-find prefix %)))
        ns-symbols (map symbol openminded-ns)]
    (doseq [ns ns-symbols]
      (-> ns
          require
          stest/instrument
          stest/enumerate-namespace))))

(instrument-namespaces-with-prefix #"openminded")