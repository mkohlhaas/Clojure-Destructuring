(ns destructuring.core)

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Destructuring in Clojure                 ;;
;; https://clojure.org/guides/destructuring ;;
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; :as
;; :keys
;; :or
;; :strs
;; :syms
;; _

;; ;;;;;;;;;;;;;;;;;;;;;;
;; What is Destructuring?
;; ;;;;;;;;;;;;;;;;;;;;;;

(let [my-line [[5 10] [10 20]]
      p1      (first  my-line)
      p2      (second my-line)
      x1      (first  p1)
      y1      (second p1)
      x2      (first  p2)
      y2      (second p2)]
  (println "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))
; (out) Line from ( 5 , 10 ) to ( 10 ,  20 )

(let [my-line [[5 10] [10 20]]
      [p1 p2] my-line
      [x1 y1] p1
      [x2 y2] p2]
  (println "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))
; (out) Line from ( 5 , 10 ) to ( 10 ,  20 )

(let [my-line [[5 10] [10 20]]
      [[x1 y1] [x2 y2]] my-line]
  (println "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")")) ; nil
; (out) Line from ( 5 , 10 ) to ( 10 ,  20 )

;; ;;;;;;;;;;;;;;;;;;;;;;;;
;; Sequential Destructuring
;; ;;;;;;;;;;;;;;;;;;;;;;;;

(let [my-vector [1 2 3]
      [x y z] my-vector]
  (println x y z))
; (out) 1 2 3

(let [my-list '(1 2 3)
      [x y z] my-list]
  (println x y z))
; (out) 1 2 3

(let [my-string "abc"
      [x y z] my-string]
  (println x y z)
  (map type [x y z]))
; (out) a b c
; (java.lang.Character java.lang.Character java.lang.Character)

(let [small-list '(1 2 3)
      [a b c d e f g] small-list]
  (println a b c d e f g))
; (out) 1 2 3 nil nil nil nil

(let [large-list '(1 2 3 4 5 6 7 8 9 10)
      [a b c] large-list]
  (println a b c))
; (out) 1 2 3

(let [names ["Michael" "Amber" "Aaron" "Nick" "Earl" "Joe"]
      [item1 item2 item3 item4 item5 item6] names]
  (println item1)
  (println item2 item3 item4 item5 item6))
; (out) Michael
; (out) Amber Aaron Nick Earl Joe

(let [names ["Michael" "Amber" "Aaron" "Nick" "Earl" "Joe"]
      [item1 & remaining] names]
  (println item1)
  (apply println remaining))
; (out) Michael
; (out) Amber Aaron Nick Earl Joe

(let [names ["Michael" "Amber" "Aaron" "Nick" "Earl" "Joe"]
      [item1 _ item3 _ item5 _] names]
  (println "Odd names:" item1 item3 item5))
; (out) Odd names: Michael Aaron Earl

(let [names ["Michael" "Amber" "Aaron" "Nick" "Earl" "Joe"]
      [item1 :as all] names]
  (println "The first name from" all "is" item1))
; (out) The first name from [Michael Amber Aaron Nick Earl Joe] is Michael

(let [numbers [1 2 3 4 5]
      [_ & remaining :as all] numbers]
  (apply prn [remaining all]))
; (out) (2 3 4 5) [1 2 3 4 5]

(let [word "Clojure"
      [x & remaining :as all] word]
  (apply prn [x remaining all]))
; (out) \C (\l \o \j \u \r \e) "Clojure"

(let [fruits ["apple" "orange" "strawberry" "peach" "pear" "lemon"]
      [item1 _ item3 & remaining :as all-fruits] fruits]
  (println "The first and third fruits are" item1 "and" item3)
  (println "These were taken from" all-fruits)
  (println "The fruits after them are" remaining))
; (out) The first and third fruits are apple and strawberry
; (out) These were taken from [apple orange strawberry peach pear lemon]
; (out) The fruits after them are (peach pear lemon)

(let [my-line [[5 10] [10 20]]
      [[x1 y1] [x2 y2]] my-line]
  (println "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))
; (out) Line from ( 5 , 10 ) to ( 10 ,  20 )

(let [my-line [[5 10] [10 20]]
      [[a b :as group1] [c d :as group2]] my-line]
  (println a b group1)
  (println c d group2))
; (out) 5 10 [5 10]
; (out) 10 20 [10 20]

;; ;;;;;;;;;;;;;;;;;;;;;;;;;
;; Associative Destructuring
;; ;;;;;;;;;;;;;;;;;;;;;;;;;

(let [client {:name        "Super Co."
              :location    "Philadelphia"
              :description "The worldwide leader in plastic tableware."}
      name        (:name client)
      location    (:location client)
      description (:description client)]
  (println name location "-" description))
; (out) Super Co. Philadelphia - The worldwide leader in plastic tableware.

(let [client {:name        "Super Co."
              :location    "Philadelphia"
              :description "The worldwide leader in plastic tableware."}
      {name :name location :location description :description} client]
  (println name location "-" description))
; (out) Super Co. Philadelphia - The worldwide leader in plastic tableware.

(let [client {:name        "Super Co."
              :location    "Philadelphia"
              :description "The worldwide leader in plastic tableware."}
      {category :category} client]
  (println category))
; (out) nil

(let [client {:name        "Super Co."
              :location    "Philadelphia"
              :description "The worldwide leader in plastic tableware."}
      {category :category :or {category "Category not found"}} client]
  (println category))
; (out) Category not found

(let [client {:name        "Super Co."
              :location    "Philadelphia"
              :description "The worldwide leader in plastic tableware."}
      {name :name :as all} client]
  (println "The name from" all "is" name))
; (out) The name from {:name Super Co., :location Philadelphia, :description The worldwide leader in plastic tableware.} is Super Co.

(let [my-map {:a "A" :b "B" :c 3 :d 4}
      {a :a, x :x, :or {x "Not found!"} :as all} my-map]
  (println "I got" a "from" all)
  (println "Where is x?" x))
; (out) I got A from {:a A, :b B, :c 3, :d 4}
; (out) Joe is a Ranger wielding a Longbow
; (out) Where is x? Not found!

(let [client {:name        "Super Co."
              :location    "Philadelphia"
              :description "The worldwide leader in plastic tableware."}
      {:keys [name location description]} client]
  (println name location "-" description))
; (out) Super Co. Philadelphia - The worldwide leader in plastic tableware.

(let [string-keys {"first-name" "Joe", "last-name" "Smith"}
      {:strs [first-name last-name]} string-keys]
  (println first-name last-name))
; (out) Joe Smith

(let [symbol-keys {'first-name "Jane", 'last-name "Doe"}
      {:syms [first-name last-name]} symbol-keys]
  (println first-name last-name))
; (out) Jane Doe

(let [multiplayer-game-state {:joe  {:class "Ranger"
                                     :weapon "Longbow"
                                     :score 100}
                              :jane {:class "Knight"
                                     :weapon "Greatsword"
                                     :score 140}
                              :ryan {:class "Wizard"
                                     :weapon "Mystic Staff"
                                     :score 150}}
      {{:keys [class weapon]} :joe} multiplayer-game-state]
  (println "Joe is a" class "wielding a" weapon))
; (out) Joe is a Ranger wielding a Longbow

;; ;;;;;;;;;;;;;;;;;
;; Keyword arguments
;; ;;;;;;;;;;;;;;;;;

(letfn [(configure [val options]
          (let [{:keys [debug verbose] :or {debug false, verbose false}} options]
            (println "val =" val " debug =" debug " verbose =" verbose)))]
  (configure 12 {:debug true}))
; (out) val = 12  debug = true  verbose = false

;; ;;;;;;;;;;;;;;;;;;;;;;;;
;; Keyword Argument Parsing
;; ;;;;;;;;;;;;;;;;;;;;;;;;

;; Associative destructuring also works with lists or sequences of key-value pairs for keyword argument parsing.
;; (Keyword arguments are optional trailing variadic arguments.)
(letfn [(configure [val & {:keys [debug verbose]
                           :or {debug false, verbose false}
                           :as opts}]
          (println "val =" val " debug =" debug " verbose =" verbose " opts =" opts))]
  (configure 10))
; (out) val = 10  debug = false  verbose = false  opts = nil

(letfn [(configure [val & {:keys [debug verbose]
                           :or {debug false, verbose false}
                           :as opts}]
          (println "val =" val " debug =" debug " verbose =" verbose " opts =" opts))]
  (configure 5 :debug true))
; (out) val = 5  debug = true  verbose = false  opts = {:debug true}

(letfn [(configure [val & {:keys [debug verbose]
                           :or {debug false, verbose false}
                           :as opts}]
          (println "val =" val " debug =" debug " verbose =" verbose " opts =" opts))]
  (configure 12 :verbose true :debug true))
; (out) val = 12  debug = true  verbose = true  opts = {:verbose true, :debug true}

(letfn [(configure [val & {:keys [debug verbose]
                           :or {debug false, verbose false}
                           :as opts}]
          (println "val =" val " debug =" debug " verbose =" verbose " opts =" opts))]
  (configure 12 {:verbose true :debug true}))
; (out) val = 12  debug = true  verbose = true  opts = {:verbose true, :debug true}

(letfn [(configure [val & {:keys [debug verbose]
                           :or {debug false, verbose false}
                           :as opts}]
          (println "val =" val " debug =" debug " verbose =" verbose " opts =" opts))]
  (configure 12 :debug true {:verbose true}))
; (out) val = 12  debug = true  verbose = true  opts = {:debug true, :verbose true}

;; ;;;;;;;;;;;;;;;;;;;
;; Namespaced keywords
;; ;;;;;;;;;;;;;;;;;;;

(let [human {:person/name    "Franklin"
             :person/age    25
             :hobby/hobbies "running"}
      {:keys        [hobby/hobbies]
       :person/keys [name age]
       :or {age 0}} human]
  (println name "is" age "and likes" hobbies))
; (out) Franklin is 25 and likes running

(let [human {:person/name "Franklin"
             :person/age  25
             :hobby/name "running"}
      {:person/keys [age]
       hobby-name   :hobby/name
       person-name  :person/name} human]
  (println person-name "is" age "and likes" hobby-name))
; (out) Franklin is 25 and likes running

;; ;;;;;;;;;;;;;;;;;;;;;;
;; auto-resolved keywords
;; ;;;;;;;;;;;;;;;;;;;;;;

;; what you would do in a project:
;; (require '[person :as p])

;; here in the REPL we do this instead:
(create-ns 'person)
(alias  'p 'person)

;; Reminder: `::` is a shortcut to represent current namespace. (Not used in the examples.)

(let [person {::p/name "Franklin", ::p/age 25}
      {:keys [::p/name ::p/age]} person]
  (println name "is" age))
; (out) Franklin is 25

(letfn [(f-with-options
          [a b & {:keys [opt1]}]
          (println "Got" a b opt1))]
  (f-with-options 1 2 :opt1 true))
; (out) Got 1 2 true

;; ;;;;;;;;;;;;;;;;;;;;
;; Where to destructure
;; ;;;;;;;;;;;;;;;;;;;;

(letfn [(print-coordinates [point]
          (let [x (first point)
                y (second point)
                z (last point)]
            (println "x:" x ", y:" y ", z:" z)))]
  (print-coordinates [1.0 2.0 3.0]))
; (out) x: 1.0 , y: 2.0 , z: 3.0

(letfn [(print-coordinates [point]
          (let [[x y z] point]
            (println "x:" x ", y:" y ", z:" z)))]
  (print-coordinates [1.0 2.0 3.0]))
; (out) x: 1.0 , y: 2.0 , z: 3.0

(letfn [(print-coordinates [[x y z]]
          (println "x:" x ", y:" y ", z:" z))]
  (print-coordinates [1.0 2.0 3.0]))
; (out) x: 1.0 , y: 2.0 , z: 3.0

(let [john-smith {:f-name "John"
                  :l-name "Smith"
                  :phone "555-555-5555"
                  :company "Functional Industries"
                  :title "Sith Lord of Git"}]
  (letfn [(print-contact-info [{:keys [f-name l-name phone company title]}]
            (println f-name l-name "is the" title "at" company)
            (println "You can reach him at" phone))]
    (print-contact-info john-smith)))
; (out) John Smith is the Sith Lord of Git at Functional Industries
; (out) You can reach him at 555-555-5555

(let [john-smith {:f-name "John"
                  :l-name "Smith"
                  :phone "555-555-5555"
                  :address {:street "452 Lisp Ln."
                            :city "Macroville"
                            :state "Kentucky"
                            :zip "81321"}
                  :hobbies ["running" "hiking" "basketball"]
                  :company "Functional Industries"
                  :title "Sith Lord of Git"}]
  (letfn [(print-contact-info
            [{:keys [f-name l-name phone company title]
              {:keys [street city state zip]} :address
              [fav-hobby second-hobby] :hobbies}]
            (println f-name l-name "is the" title "at" company)
            (println "You can reach him at" phone)
            (println "He lives at" street city state zip)
            (println "Maybe you can write to him about" fav-hobby "or" second-hobby))]
    (print-contact-info john-smith)))
; (out) John Smith is the Sith Lord of Git at Functional Industries
; (out) You can reach him at 555-555-5555
; (out) He lives at 452 Lisp Ln. Macroville Kentucky 81321
; (out) Maybe you can write to him about running or hiking

;; ;;;;;;
;; Macros
;; ;;;;;;

;; The `destructure` function is designed to be invoked in a macro.
;; Takes a form and return a form.
(destructure '[[x & remaining :as all] [1 2 3]])

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; The complete guide to Clojure destructuring.                                      ;;
;; https://blog.brunobonacci.com/2014/11/16/clojure-complete-guide-to-destructuring/ ;;
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Destructuring maps as key-value pairs
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(let [contact
      {:firstname "John"
       :lastname  "Smith"
       :age       25
       :phone     "+44.123.456.789"
       :emails    "jsmith@company.com"}]
  (map (fn [[k v]] (str k " -> " v)) contact))
;; (":firstname -> John"
;;  ":lastname -> Smith"
;;  ":age -> 25"
;;  ":phone -> +44.123.456.789"
;;  ":emails -> jsmith@company.com")

;; ;;;;;;;;;;;;;;;;;;;;
;; Nested destructuring
;; ;;;;;;;;;;;;;;;;;;;;

(let [contact
      {:firstname "John"
       :lastname  "Smith"
       :age       25
       :contacts {:phone "+44.123.456.789"
                  :emails {:work "jsmith@company.com"
                           :personal "jsmith@some-email.com"}}}
      {lastname :lastname} contact]
  (println lastname))
; (out) Smith

;; One nested level
(let [contact
      {:firstname "John"
       :lastname  "Smith"
       :age       25
       :contacts {:phone "+44.123.456.789"
                  :emails {:work "jsmith@company.com"
                           :personal "jsmith@some-email.com"}}}
      {lastname :lastname
       {phone   :phone} :contacts} contact]
  (println lastname phone))
; (out) Smith +44.123.456.789

(let [contact
      {:firstname "John"
       :lastname  "Smith"
       :age       25
       :contacts {:phone "+44.123.456.789"
                  :emails {:work "jsmith@company.com"
                           :personal "jsmith@some-email.com"}}}
      {:keys [firstname lastname]
       {:keys [phone]} :contacts} contact]
  (println firstname lastname phone)) ; nil
; (out) John Smith +44.123.456.789

(let [contact {:firstname "John"
               :lastname  "Smith"
               :age       25
               :contacts {:phone "+44.123.456.789"
                          :emails {:work "jsmith@company.com"
                                   :personal "jsmith@some-email.com"}}}
      {:keys [firstname lastname]
       {:keys [phone]
        {:keys [work personal]} :emails} :contacts} contact]
  (println firstname lastname phone work personal))
; (out) John Smith +44.123.456.789 jsmith@company.com jsmith@some-email.com

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Less common destructuring forms
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Destructuring vectors by keys
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; you can use map’s destructuring methods to destructure vectors

;; Vectors are just like maps.
;; keys are the indexes
(let [{one 1 two 2} [0 1 2]]
  (println one two))
; (out) 1 2

(let [{v1 100 v2 200} (apply vector (range 500))]
  (println v1 v2))
; (out) 100 200

;; ;;;;;;;;;;;;;;;;;;;
;; Set’s destructuring
;; ;;;;;;;;;;;;;;;;;;;

;; Sets are just like maps.
;; keys are the values
(let [{:strs [blue white black]} #{"blue" "white" "red" "green"}]
  (println blue white black))
; (out) blue white nil

#_{:clj-kondo/ignore [:unused-binding]}
#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
;; list files
(defn ls [path & flags]
  (let [{:keys [long-format human-readable sort-by-time]} (set flags)]
    (when long-format '(do something))))
