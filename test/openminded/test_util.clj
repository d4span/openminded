(ns openminded.test-util
  (:require [clojure.test :refer :all]
            [clojure.spec.test.alpha :as stest]
            [openminded.tree :as tree]
            [openminded.tree.spec :as spec]))

(defn test-function
    ([sym num-tests]
        (println "Running tests for" sym)
        (let [result (stest/check sym {:clojure.spec.test.check/opts {:num-tests num-tests}})]
            (println "Result: "  result)))
    ([sym] (test-function sym 24))
    ([a b c] (println "Too many arguments!")))
