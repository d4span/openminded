(ns openminded.tree-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]
            [clojure.spec.gen.alpha :as gen]
            [clojure.test :refer :all]
            [openminded.tree :as tree]
            [openminded.test-util :refer [test-syms]]))

(def tree-gen
  (gen/fmap
   (fn [[root steps]]
     (loop [nodes [root]
            node-set #{root}
            steps-left steps
            tree []]
       (if (empty? steps-left)
         tree
         (let [[parent value children] (first steps-left)
               parent-node (nth nodes (mod parent (count nodes)))
               children-valid (filter #(not (contains? node-set %)) children)]
           (recur (apply conj nodes children-valid)
                  (apply conj node-set children-valid)
                  (rest steps-left)
                  (conj tree
                        {:id parent-node :value value :children children}))))))
   (gen/tuple
    (gen/simple-type-printable)
    (gen/vector
     (gen/tuple (gen/int) (gen/any) (gen/vector (gen/simple-type-printable)))))))

(deftest namespace-specs
  (testing "if specs are satisfied"
    (is (empty?
         (test-syms (stest/enumerate-namespace `openminded.tree)
                    {:clojure.spec.test.check/opts {:num-tests 24}})))))
