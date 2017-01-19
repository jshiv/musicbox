from pyo import *
pm_get_input_devices()

# Set Up Server
s = Server()
s.setMidiInputDevice(0) # Must be called before s.boot()
s.boot()
s.start()

midi = Notein() # The defaults are more than adequate
bend = Bendin(brange=2, scale=1)

ctl = Midictl(1, minscale=0, maxscale=1)



pitch = MToF(midi['pitch'] * bend)


amp = MidiAdsr(midi['velocity'], attack=.01, decay=0.05, sustain=0.7, release=0.1)

# wave = PartialTable()
wave = AtanTable()
# wave = ExpTable()
# wave = SquareTable()
# wave = CurveTable()
# wave = HannTable()
# wave = ChebyTable()
# wave = SincTable()
# wave = WinTable()

osc = Osc(wave, freq=pitch, mul=amp)
# osc.out()



# ctl = Midictl(1, minscale=100, maxscale=1000)

# moog = MoogLP(osc)
verb = Freeverb(osc)#.out()

ctl = Midictl(1, minscale=0, maxscale=1000)
out = AllpassWG(verb, ctl, mul=.5).out()




s.gui(locals())

