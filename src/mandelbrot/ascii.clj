;; Clojure solution for Lisp Quiz #2, http://www.lispforum.com/viewtopic.php?f=32&t=334
;; based loosely on http://bc.tech.coop/blog/040811.html
;;
;; by Brian Carper, http://briancarper.net/blog/455/clojure-ascii-mandelbrot-set
;;
;; Usage:
;;
;; (use 'mandelbrot.ascii)
;; (mandelbrot -2.0 1.0 -1.5 1.5)

(ns mandelbrot.ascii
  (:refer-clojure :exclude [+ *])
  (:use (clojure.contrib complex-numbers)
        (clojure.contrib.generic [arithmetic :only [+ *]]
                                 [math-functions :only [abs]])))

(defn mandelbrot-seq [x y]
  (let [z (complex x y)]
    (iterate #(+ z (* % %)) z)))

(defn mandelbrot-char [x y]
  (loop [c 126
         m (mandelbrot-seq x y)]
    (if (and (< (abs (first m)) 2)
             (> c 32))
      (recur (dec c) (rest m))
      (char c))))

(defn mandelbrot-line [xs y]
  (apply str (map #(mandelbrot-char % y) xs)))

(defn m-range [min max num-steps]
  (range min max (/ (- max min) num-steps)))

(defn mandelbrot [rmin rmax imin imax]
  (let [rows 30
        cols 50
        xs (m-range rmin rmax cols)
        ys (m-range imin imax rows)]
    (dorun (map #(println (mandelbrot-line xs %)) ys))))

; ~~~~~~~~~~~~}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}
; ~~~~~~~~~~}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}
; ~~~~~~~~~}}}}}}}}}}}}|||||||||}}}}}}}}}}}}}}}}}}}}
; ~~~~~~~}}}}}}}}|||||||||||||||||||||}}}}}}}}}}}}}}
; ~~~~~~}}}}}||||||||||||||{{{{zlxz{{{||||}}}}}}}}}}
; ~~~~~}}}}|||||||||||||{{{{{zzyxpvlz{{{||||}}}}}}}}
; ~~~~}}}|||||||||||||{{{{{{zzyxvnpwyzz{{{||||}}}}}}
; ~~~}}|||||||||||||{{{{{{zzyyws   .vyzzz{{|||||}}}}
; ~~~}||||||||||||{{{{{zzxwwwvus   muvxyywz{|||||}}}
; ~~}|||||||||||{{{zzzzyyu= p         oteqpz{|||||}}
; ~~||||||||||{zzzzzzyyyvtm              oxz{{|||||}
; ~}|||||{{{zyvwxxxxxxxwrG                vuz{|||||}
; ~||{{{{{zzzywsMsqRovvs                  pxz{{|||||
; ~|{{{{{zzzyxsq      pj                  `xz{{|||||
; ~{{{{yyyxwsrp                           wyz{{|||||
; ~?:3 3 #                              ovxzz{{|||||
; ~{{{{yyyxwsrp                           wyz{{|||||
; ~|{{{{{zzzyxsq      pj                  `xz{{|||||
; ~||{{{{{zzzywsMsqRovvs                  pxz{{|||||
; ~}|||||{{{zyvwxxxxxxxwrG                vuz{|||||}
; ~~||||||||||{zzzzzzyyyvtm              oxz{{|||||}
; ~~}|||||||||||{{{zzzzyyu= p         oteqpz{|||||}}
; ~~~}||||||||||||{{{{{zzxwwwvus   muvxyywz{|||||}}}
; ~~~}}|||||||||||||{{{{{{zzyyws   .vyzzz{{|||||}}}}
; ~~~~}}}|||||||||||||{{{{{{zzyxvnpwyzz{{{||||}}}}}}
; ~~~~~}}}}|||||||||||||{{{{{zzyxpvlz{{{||||}}}}}}}}
; ~~~~~~}}}}}||||||||||||||{{{{zlxz{{{||||}}}}}}}}}}
; ~~~~~~~}}}}}}}}|||||||||||||||||||||}}}}}}}}}}}}}}
; ~~~~~~~~~}}}}}}}}}}}}|||||||||}}}}}}}}}}}}}}}}}}}}
; ~~~~~~~~~~}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}

