(ns nano-id.custom-test
  (:require
    #?(:clj  [clojure.test :refer :all]
       :cljs [cljs.test :refer-macros [deftest is testing]])
    [nano-id.util :as util]
    [nano-id.custom :refer [generate]]))


(def alphabet "abcdefghijklmnopqrstuvwxyz")

(def nano-id (generate alphabet))
(def nano-ids (repeatedly #(nano-id 5)))


(deftest test-custom-nano-id
  (testing "has flat distribution")
    (let [n      100000
          ids    (take n nano-ids)
          id-len (count (first ids))
          ab-len (count alphabet)
          chars  (->> ids
                      (map frequencies)
                      (reduce #(merge-with + %1 %2) {}))]
      (doseq [[_ freq] chars]
        (let [distribution (/ (* freq ab-len)
                              (* n    id-len))]
          (is (util/close? distribution 1.0))))))
