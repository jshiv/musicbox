from pyo import *
pm_get_input_devices()

# Set Up Server
s = Server()
s.setMidiInputDevice(0) # Must be called before s.boot()
s.boot()
s.start()

midi = Notein() # The defaults are more than adequate


bend = Bendin(brange=2, scale=1)
pitch = MToF(midi['pitch'] * bend)





amp = MidiAdsr(midi['velocity'], attack=.01, decay=0.05, sustain=0.7, release=0.1)

# wave = PartialTable()
#wave = AtanTable()
# wave = ExpTable()
# wave = SquareTable()
# wave = CurveTable()
wave = HannTable()
# wave = ChebyTable()
# wave = SincTable()
# wave = WinTable()

osc = Osc(wave, freq=pitch, mul=amp)
# osc.out()

moog = MoogLP(osc)
verb = Freeverb(moog)#.out()

ctl = Midictl(1, minscale=0, maxscale=5)
chorus = Chorus(verb, depth=ctl, bal=.5)


delay = Delay(chorus, delay=0, feedback=.5, maxdelay=1).out()


#ctl = Midictl(1, minscale=100, maxscale=1000)
#fqshft = FreqShift(delay, shift=ctl).out()

# distro = Disto(effect, drive=.5).out()
# verb = Beat(osc).out()



s.gui(locals())
