(ns nano-id.custom-test
  (:require
    #?(:clj  [clojure.test :refer :all]
       :cljs [cljs.test :refer-macros [deftest is testing]])
    [nano-id.custom :refer [nano-id]]))


(def alphabet "abcdefghijklmnopqrstuvwxyz")

(def nano-ids (repeatedly #(nano-id alphabet 5)))


(defn close? [^double x ^double y]
  (let [precision 0.05]
    (< (Math/abs (- x y)) precision)))


(deftest test-custom-nano-id
  (testing "has flat distribution")
    (let [n      100000
          ids    (take n nano-ids)
          id-len (count (first ids))
          ab-len (count alphabet)
          chars  (->> ids
                      (map frequencies)
                      (reduce #(merge-with + %1 %2) {}))]
      (doseq [[ch freq] chars]
        (let [distribution (/ (* freq ab-len)
                              (* n    id-len))]
          (is (close? distribution 1.0) ch)))))
