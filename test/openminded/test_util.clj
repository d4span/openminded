(ns openminded.test-util
  (:require [clojure.test :refer :all]
            [clojure.spec.test.alpha :as stest]))

(defn- prepare-result [result]
  (let [passed (-> result first :clojure.spec.test.check/ret :pass?)]
    (if-not passed
      [passed
       (str (-> result
                first
                :clojure.spec.test.check/ret
                :shrunk
                :result))]
      [passed])))

(defn check
  ([syms]
   (-> (stest/check syms)
       prepare-result))

  ([syms num-tests]
   (-> (stest/check syms {:clojure.spec.test.check/opts {:num-tests num-tests}})
       prepare-result)))