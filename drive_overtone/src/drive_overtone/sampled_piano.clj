(use 'overtone.live)
(use 'overtone.inst.sampled-piano)
(use 'overtone.inst.sampled-flute)
(event-debug-on)


(saw-wave (midi->hz (note :A4)))
(saw-wave (midi->hz (note :C5)))
(saw-wave (midi->hz (note :C4)))


(on-event [:midi :note-on]
                (fn [e]
                  (let [note (:note e)
                     vel  (:velocity-f e)]
                        (triangle-wave :freq (midi->hz note)  )))
                  ::keyboard-handler)

<64;55;15M


(demo 7 (lpf (mix (saw [50 (line 100 1600 5) 101 100.5]))
                               (lin-lin (lf-tri (line 2 20 5)) -1 1 400 4000)))


(definst kick [freq 120 dur 0.3 width 0.5]
  (let [freq-env (* freq (env-gen (perc 0 (* 0.99 dur))))
        env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
        sqr (* (env-gen (perc 0 0.01)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        drum (+ sqr (* env src))]
    (compander drum drum 0.2 1 0.1 0.01 0.01)))

;(kick)

(definst c-hat [amp 0.8 t 0.04]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 880 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

;(c-hat)



(def metro (metronome 128))

M(metro) ; => current beat number
(metro 100) ; => timestamp of 100th beat


(defn player [beat]
  (at (metro beat) (kick))
  (at (metro (+ 0.5 beat)) (c-hat))
  (apply-by (metro (inc beat)) #'player (inc beat) []))

(player (metro))

(stop)

(definst sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))
(sin-wave :freq 220)


(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))
(saw-wave)

(definst square-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-pulse:ar freq)
     vol))
(saw-wave)

(definst noisey [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (pink-noise) ; also have (white-noise) and others...
     vol))
(noisey)

(definst triangle-wave [freq 440 attack 0.01 sustain 0.1 release 0.4 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-tri freq)
     vol))
(triangle-wave)

(square-wave)


(demo 10 (lpf (saw 100) (mouse-x 40 5000 EXP)))

(demo 10 (hpf (saw 100) (mouse-x 40 5000 EXP)))

(demo 30 (bpf (saw 50) (mouse-x 40 5000 EXP) (mouse-y 0.01 1 LIN)))

;; here we generate a pulse of white noise, and pass it through a pluck filter
;; with a delay based on the given frequency
(let [freq 220]
   (demo (pluck (* (white-noise) (env-gen (perc 0.001 2) :action FREE)) 1 3 (/ 1 freq))))




;;
;; Binaural Beat Synthesis:
;; Generates binaural beats given the provided carrier and desired
;; frequency. Brown noise is used to soften the background and
;; block out outside noise.

;; freq    effect
;;   < 4   Delta, Sleep
;; 3 - 7   Theta, relaxation, meditation
;; 7 - 13  Alpha, Relaxation while Awake
;;
(defsynth bbeat [amp 0.3
                 carrier 440
                 freq 4.5]
  (let [freq-a carrier
        freq-b (+ carrier freq)
        left (* 0.8 (sin-osc freq-a))
        right (* 0.8 (sin-osc freq-b))]
    (out 0 (* amp left))
    (out 1 (* amp right))))


(stop)
 (demo (bbeat))
(defcgen snare-drum
  "basic synthesised snare drum"
  [bpm {:default 120 :doc "tempo of snare in beats per minute"}]
  (:ar
   (let [snare (* 3 (pink-noise [1 1]) (apply + (* (decay (impulse (/ bpm 240) 0.5) [0.4 2]) [1 0.05])))
         snare (+ snare (bpf (* 4 snare) 2000))]
     (clip2 snare 1))))

(defcgen kick-drum
  "basic synthesised kick drum"
  [bpm {:default 120 :doc "tempo of kick in beats per minute"}
   pattern {:default [1 0] :doc "sequence pattern of beats"}]
  (:ar
   (let [kickenv (decay (t2a (demand (impulse:kr (/ bpm 30)) 0 (dseq pattern INF))) 0.7)
         kick (* (* kickenv 7) (sin-osc (+ 40 (* kickenv kickenv kickenv 200))))]
     (clip2 kick 1))))

(definst drums [bpm 120]
(let [kick (kick-drum bpm :pattern [1 0 0 0 0 0 1 0 1 0 0 1 0 0 0 0])
       snare (snare-drum bpm)]
   (clip2 (+ kick snare) 1)))

(bbeat 0.2 440 3)
(stop)


(demo (saw :freq 110))

(stop)
(demo 60
      (let [bpm     120
            ;; create pool of notes as seed for random base line sequence
            notes   [40 41 28 28 28 27 25 35 78]
            ;; create an impulse trigger firing once per bar
            trig    (impulse:kr (/ bpm 120))
            ;; create frequency generator for a randomly picked note
            freq    (midicps (lag (demand trig 0 (dxrand notes INF)) 0.25))
            ;; switch note durations
            swr     (demand trig 0 (dseq [1 6 6 2 1 2 4 8 3 3] INF))
            ;; create a sweep curve for filter below
            sweep   (lin-exp (lf-tri swr) -1 1 40 3000)
            ;; create a slightly detuned stereo sawtooth oscillator
            wob     (mix (saw (* freq [0.99 1.01])))
            ;; apply low pass filter using sweep curve to control cutoff freq
            wob     (lpf wob sweep)
            ;; normalize to 80% volume
            wob     (* 0.8 (normalizer wob))
            ;; apply band pass filter with resonance at 5kHz
            wob     (+ wob (bpf wob 1500 2))
            ;; mix in 20% reverb
            wob     (+ wob (* 0.2 (g-verb wob 9 0.7 0.7)))

            ;; create impulse generator from given drum pattern
            kickenv (decay (t2a (demand (impulse:kr (/ bpm 30)) 0 (dseq [1 0 0 0 0 0 1 0 1 0 0 1 0 0 0 0] INF))) 0.7)
            ;; use modulated sine wave oscillator
            kick    (* (* kickenv 7) (sin-osc (+ 40 (* kickenv kickenv kickenv 200))))
            ;; clip at max volume to create distortion
            kick    (clip2 kick 1)

            ;; snare is just using gated & over-amplified pink noise
            snare   (* 3 (pink-noise) (apply + (* (decay (impulse (/ bpm 240) 0.5) [0.4 2]) [1 0.05])))
            ;; send through band pass filter with peak @ 2kHz
            snare   (+ snare (bpf (* 4 snare) 2000))
            ;; also clip at max vol to distort
            snare   (clip2 snare 1)]
   ;; mixdown & clip
   (clip2 (+ wob kick snare) 1)))

(stop)
(demo 30
      (let [trig (coin-gate 0.5 (impulse:kr 2))
            note (demand trig 0 (dseq (shuffle (map midi->hz (conj (range 24 45) 22))) INF))
            sweep (lin-exp (lf-saw (demand trig 0 (drand [1 2 2 3 4 5 6 8 16] INF))) -1 1 40 5000)

            son (mix (lf-saw (* note [0.99 1 1.01])))
            son (lpf son sweep)
            son (normalizer son)
            son (+ son (bpf son 2000 2))

            ;;special flavours
            ;;hi manster
            son (select (< (t-rand:kr :trig trig) 0.05) [son (* 4 (hpf son 1000))])

            ;;sweep manster
            son (select (< (t-rand:kr :trig trig) 0.05) [son (* 4 (hpf son sweep))])

            ;;decimate
            son (select (< (t-rand:kr :trig trig) 0.05) [son (round son 0.1)])

            son (tanh (* son 5))
            son (+ son (* 0.3 (g-verb son 10 0.1 0.7)))
            son (* 0.3 son)]

        [son son]))
