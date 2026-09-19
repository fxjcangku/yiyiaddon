package com.yiyiaddon.d.c;

public record d(d.a a, int n, long f) {
   private static final d a = new d(d.a.PASS, 0, 0L);

   public static d a() {
      return a;
   }

   public static d b() {
      return new d(d.a.CANCEL, 0, 0L);
   }

   public static d a(int var0) {
      return new d(d.a.REPLACE_INPUT, var0, 0L);
   }

   public static d a(long var0) {
      return new d(d.a.DELAY, 0, Math.max(0L, var0));
   }

   public boolean l() {
      if (this.a == d.a.PASS) {
         switch ((int)com.yiyiaddon.m.b.a<"scjzn5hdcc3gn","MlODQ+LunTw9eC5ivJbsZlNhlmLycLFXnUY7PpiXXzk=",-390960507695622587,-2094407026717575631,9130289695771472811,6930990446717318135>()) {
            case 157293042:
               switch ((int)com.yiyiaddon.m.b.a<"sct6lsrft3u2n","MOY+lPLaWfRCjIP2jJIADigA/S2cf0XuFtsWodW5ESg=",-2387848250958469056,-6845190845834885127,1608905286824676,1592890985214258696>()) {
                  case -416573849:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s34cg9r19eaa4w","iYn5cWBRwA9OisR90a7h6+2UNcssf6mpiQxbZSU/Gz8=",1767604255674914872,4396156943161641107,-275112452402673762,2231918960133178862>()) {
            case -423941359:
               return false;
            default:
               throw null;
         }
      }
   }

   public int l() {
      return this.n;
   }

   public long d() {
      return this.f;
   }

   public enum a {
      PASS,
      CANCEL,
      REPLACE_INPUT,
      DELAY;
   }
}
