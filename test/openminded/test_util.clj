(ns openminded.test-util
  (:require [clojure.test :refer :all]
            [clojure.spec.test.alpha :as stest]))

(defn test-syms
  [syms opts]
  (let [result (stest/check syms opts)
        output (map (fn [r] [(:sym r)
                             (-> (:clojure.spec.test.check/ret r)
                                 :shrunk
                                 :smallest)
                             #(:clojure.spec.test.check/ret r)])
                    result)
        failures (filter (fn [[_ _ ret]] (false? (:pass? (ret)))) output)]
    [output, failures]))
