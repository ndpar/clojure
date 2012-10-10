;; http://www.codingdojo.org/cgi-bin/wiki.pl?KataBankOCR
;;
;; Author Ben Moss

(ns dojo.ocr
  "http://github.com/drteeth/bank-ocr-clojure"
  (:use [clojure.string :only (split)]))

(defn lines [input]
  "Break a block of OCR'd text into lines, removing the leading linebreak"
  (rest (split input #"\n")))

(defn cols [line]
  "Breaks a string representing a line into a list of 3-string groups"
  (map (partial apply str) (partition 3 line)))

(defn parse-it [input]
  "Breaks a block of digits into a list of list of 3-string groups"
  (map cols (lines input)))

(def value-map {
  " _ | ||_|" 0
  "     |  |" 1
  " _  _||_ " 2
  " _  _| _|" 3
  "   |_|  |" 4
  " _ |_  _|" 5
  " _ |_ |_|" 6
  " _   |  |" 7
  " _ |_||_|" 8
  " _ |_| _|" 9
  })

(defn value-of [digit]
  "Returns the numeric value given a 3x3 matrix of strings"
  (value-map digit))

(defn read-digits [digits]
  "Reads a block of digits and returns a vector of the digits that were parsed"
  (map value-of (apply map str (parse-it digits))))

