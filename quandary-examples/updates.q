int main(int arg) {
  mutable Q blah = 5 . nil;
  int dummy1 = setLeft(blah, 8);
  int dummy2 = setRight(blah, nil . 9);
  return blah;
}
