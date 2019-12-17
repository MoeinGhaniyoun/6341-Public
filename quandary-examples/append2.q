/* Test memory management */
mutable Q main(int arg) {
  mutable Ref list = 2 . nil;
  list = addR(list, 3);
  list = addR(list, 5);
  return list;
}

Ref addR(Ref list, int elem) {
  if (isNil(list) != 0) {
    return elem . nil;
  }
  Q left = left(list);
  Q right = right(list);
  free(list);
  return left . addR(right, elem);
}
