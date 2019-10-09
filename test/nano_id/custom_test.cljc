(ns nano-id.custom-test
  (:require
    #?(:clj  [clojure.test :refer :all]
       :cljs [cljs.test :refer-macros [deftest is testing]])
    [nano-id.util :as util]
    [nano-id.custom :refer [generate]]))


(deftest test-custom-nano-id
  (testing "has flat distribution"
    (let [alphabet "abcdefghijklmnopqrstuvwxyz"
          nano-id  (generate alphabet)
          n        100000
          id-len   5
          ab-len   (count alphabet)
          nano-ids (repeatedly #(nano-id id-len))
          xf       (comp (map frequencies) (take n))
          chars    (transduce xf (partial merge-with +) {} nano-ids)]
      (doseq [[_ freq] chars]
        (let [distribution (/ (* freq ab-len)
                              (* n    id-len))]
          (is (util/close? distribution 1.0))))))

  (testing "custom random"
    (let [alphabet "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-"
          prng     (fn [n] (take n (iterate #(unsigned-bit-shift-right % 6) 2377900801)))
          gen-id   (generate alphabet prng)]
      (is (= (gen-id 6) "2DjzC1")))))
