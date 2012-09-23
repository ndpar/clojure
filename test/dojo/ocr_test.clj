;; Author Ben Moss http://github.com/drteeth/bank-ocr-clojure

(ns dojo.ocr-test
  (:use dojo.ocr clojure.test))

(def nums
"
AAADDDGGGJJJMMMPPPSSSVVVYYY
BBBEEEHHHKKKNNNQQQTTTWWWZZZ
CCCFFFIIILLLOOORRRUUUXXX111")

(def one-through-nine
"
    _  _     _  _  _  _  _ 
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|")

(def n981746374
"
 _  _     _     _  _  _    
|_||_|  |  ||_||_  _|  ||_|
 _||_|  |  |  ||_| _|  |  |")

(deftest ocr-tests
  (is (= 3 (count (lines nums))))
  (is (= ["AAA" "DDD" "GGG" "JJJ" "MMM" "PPP" "SSS" "VVV" "YYY"]
         (cols (first (lines nums)))))
  (is (= '(("AAA" "DDD" "GGG" "JJJ" "MMM" "PPP" "SSS" "VVV" "YYY") ("BBB" "EEE" "HHH" "KKK" "NNN" "QQQ" "TTT" "WWW" "ZZZ") ("CCC" "FFF" "III" "LLL" "OOO" "RRR" "UUU" "XXX" "111"))
         (parse-it nums)))
  (is (= 3 (value-of " _  _| _|")))
  (is (= [1 2 3 4 5 6 7 8 9] (read-digits one-through-nine)))
  (is (= [9 8 1 7 4 6 3 7 4] (read-digits n981746374))))
