#_{:clj-kondo/ignore [:refer-all]}
(ns destructuring.core-test
  (:require [clojure.test       :refer :all]
            [destructuring.core :refer :all]))

(deftest a-test
  (testing "FIXED"
    (is (= 1 1))))
