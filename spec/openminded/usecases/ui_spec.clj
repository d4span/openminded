(ns openminded.usecases.ui-spec
  (:require [clojure.spec.alpha :as s]
            [openminded.usecases.ui :as ui]))

(s/fdef ui/show
        :args (s/cat :this any? :tree ::tree)
        :ret any?)