mutable Q main(int arg) {
    Ref flagAndData = 0 . (0 . 0);
    return [ produce(flagAndData) . consume(flagAndData) ] ;
}

mutable Q produce(Ref flagAndData) {
    setRight(flagAndData, (42 . 42 * 42));
    acq(flagAndData);
    setLeft(flagAndData, 1);
    rel(flagAndData);
    return nil;
}

mutable Q consume(Ref flagAndData) {
    mutable int done = 0;
    while (done == 0) {
        acq(flagAndData);
        done = (int)left(flagAndData);
        rel(flagAndData);
    }
    return right(flagAndData);
}
