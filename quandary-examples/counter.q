
mutable int doLeftInc(Ref counters) {
    mutable int i = 0;
    while (i < 10000) {
        acq(counters);
        int value = (int)left(counters);
        setLeft(counters, value + 1);
        rel(counters);
        i = i + 1;
    }
    return 0;
}

mutable int doRightInc(Ref counters) {
    mutable int i = 0;
    while (i < 10000) {
        int value = (int)right(counters);
        setRight(counters, value + 1);
        i = i + 1;
    }
    return 0;
}

mutable Q main(int arg) {
    mutable Ref counters = 0 . 0;
    int dummy = [ doRightInc(counters) + doRightInc(counters) ];
    return counters;
}
