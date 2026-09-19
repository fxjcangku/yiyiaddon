package com.yiyiaddon.e.i.d;

import java.util.Objects;
import java.util.UUID;

public record l(UUID c, int fD, a f, m a, e a, a g, c c, n a, k b) {
   public static final int fE = -1;

   public l(UUID c, int fD, a f, m a, e a, a g, c c, n a, k b) {
      Objects.requireNonNull(
         c,
         (String)com.yiyiaddon.m.b.a<"s24toqv4yhyrll","hMMcr8yCMIF8MmXjbq+F4DmSAAvtQ1rRMEFEaQf8Xf5I02KT",1837156112414115449,7142241009744057220,5521103641852266672,4599626558932387110>()
      );
      Objects.requireNonNull(
         f,
         (String)com.yiyiaddon.m.b.a<"s3ufl87ce7yp6e","5o5I70NkTIAysZRkC2YMkdi3412PL9kAucYaAUMTUg6UTLO5XFWL2fytoBI=",7751510266993530762,7046096859959593796,-8566218247613508008,6822288779732809344>()
      );
      Objects.requireNonNull(
         a,
         (String)com.yiyiaddon.m.b.a<"s25x4b9xrq1dzj","0v4NFisXX/SWbMdrypoIzVwFzuG3xHqUSST8S6wmcZkTdbDbKJ6S3A==",-6250910791645611558,-668516633506132139,-4104608609796201841,1523492306812183928>()
      );
      Objects.requireNonNull(
         a,
         (String)com.yiyiaddon.m.b.a<"s2rh0q9kh6zad1","ovcPpMX2phanLIIqNvBizecwIjZ7acHrz7cJnDBkz8BCaGHtPbnAPRYWHvtP2oepSLNq4tFD",-6091056314350644936,-1142108653899653784,-3754914333414213802,7783495916361343976>()
      );
      Objects.requireNonNull(
         a,
         (String)com.yiyiaddon.m.b.a<"s36phapdcdp6z1","AIKg9e3lKSskn88ddT0VE6pRHwAOKFslkM+N/Nb68tPL3RTB9vlLHdYAgN6pTknYGAQ=",-2522074309794323428,8972641739443973742,-4440909570179722821,-4983024656260284609>()
      );
      if (fD < -1) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"sy19q6ppog7c3","Wpnkja3MX/OtHctqzdjd65d+QNGpT88wRuQay5ke7M4bc0NRCKtCpFQBPhuTZUvTV7EJglqpNL4fTFmd",-2548274489036062997,8695644698397632088,-6145295039493419368,7220989714022840984>()
         );
      }

      if (a == m.UNRESOLVED && fD != -1) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s14ewmovnpxbb6","gEMmHDKj3j9VI//4ymwrWBw127a4CvJh3zv2fDCBXC/40jxF0KDvc9YKzpXPQZav3tNHzcG4KE4K6F+fV2A=",-1002044859092391205,6317945849437223866,7357235536561893566,-7644705837219430345>()
         );
      }

      if (a != m.UNRESOLVED && fD == -1) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s3nuy7q9pmhka1","xm6D9hNzOOQSansnR6Lsu2lWxt7RYG7bpvccotROytrU9RzP5luVvK7BTKrX0ROXaPrrXxCWyktchu8zLCg=",2317512258301693147,9142142699694084784,-9103947943832946003,8807966569472956559>()
         );
      }

      boolean var10 = a == e.LOCATED || a == e.PLACING || a == e.PLACED || a == e.REMOVING;
      if (var10 && g == null) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s2r7kwwpgwpb34","1AY8Nk3r9+98ZB+eqIyQTSYJocrFMatzOnPk8fsFkP2+XnRlL/0yjqI4qwu4Vz0Q2zcPOQU6MTQ=",-2658542379410776194,2719816606068120001,3180282699314139496,-8702048080099018306>()
         );
      }

      if (a == e.UNLOCATED && g != null) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s1l25ka4syyg74","l6RBSx99iB1OYksVyy8Beiskd89E9T6V9EYKVsFFlF2QVF4pdzE28Nt8pQC1QL6E/n4bdFCB",-8828652778276389963,-1806702902656225559,-8063899759970708188,-3572686319633164107>()
         );
      }

      if (b != null) {
         if (!c.equals(b.b())) {
            throw new IllegalArgumentException(
               (String)com.yiyiaddon.m.b.a<"s30one5awk0nik","fAgWPkQSepyvb3wghBhwo9y/NDpfQkwchrrw8mwh3K6mQJ0vLPAWVpnhQxEkN6CpOSs=",-3330042643401100537,2456240873888059700,843198713706877161,-326501183388762071>()
            );
         }

         if (c != b.b()) {
            throw new IllegalArgumentException(
               (String)com.yiyiaddon.m.b.a<"sn9ak06sv7lwm","9Wi6FwNKOmlnjFJmgXYCakczmk4RmEMDpqrVOlyG+WGGMfd/xVvM+F/B4/YSJmDD4mSyAw==",4188520441164913476,-1695801584436353534,-1030498861165294621,1328729333673492273>()
            );
         }

         if (!b.e().equals(g)) {
            throw new IllegalArgumentException(
               (String)com.yiyiaddon.m.b.a<"s39zxm7ac9p8pm","G9no+FL8CqXn8CJ/8dwalOW0OrrAHfOsrxbQtJJgjWijCyWQ/C/Clf45jXglz5AgsOAGIUXVc+8=",5095125374997153147,354511504266653367,6553080927237755982,4822015296829700737>()
            );
         }
      }

      this.c = c;
      this.fD = fD;
      this.f = f;
      this.a = a;
      this.a = a;
      this.g = g;
      this.c = c;
      this.a = a;
      this.b = b;
   }

   public l(UUID var1, int var2, a var3, m var4, e var5, a var6) {
      this(var1, var2, var3, var4, var5, var6, null, n.NOT_OPENED, null);
   }

   public l(UUID var1, int var2, a var3) {
      this(var1, var2, var3, m.SELECTED, e.UNLOCATED, null);
   }

   public l a(a var1) {
      return new l(this.c, this.fD, var1, this.a, this.a, this.g, this.c, this.a, this.b);
   }

   public l a(int var1, a var2) {
      return new l(this.c, var1, var2, m.AVAILABLE, this.a, this.g, this.c, this.a, this.b);
   }

   public l a() {
      return new l(this.c, -1, this.f, m.UNRESOLVED, this.a, this.g, this.c, this.a, this.b);
   }

   public l a(m var1) {
      int var10000;
      if (var1 == m.UNRESOLVED) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"stp7jffwikxz8","xN+28WuGJbPKv2LRRnuvHTLSCL2KUd8uyK51/6FNiAk=",7279506840810472367,4420238463463807560,-4152776038954008630,2783159602647644349>()) {
            case -1873225725:
               var10000 = -1;
               switch ((int)com.yiyiaddon.m.b.a<"s17u2q4ecbsam1","3XAuFINQyimw31lFj/qNhc2+ohyUVrTH3xo3QcZPCOk=",339422210503747805,-7232930981804676117,-7672125899488006905,7311269968383912275>()) {
                  case -1159624766:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.fD;
         switch ((int)com.yiyiaddon.m.b.a<"s2yiwvow95ddbg","zqkbql+/eVg8FJfKiEUeVrB7LajWP02yQyQCrAA+63Q=",-3070665477882735951,-8141118658779290376,8731349702939411921,-8566935645485725960>()) {
            case 843942392:
               break;
            default:
               throw null;
         }
      }

      int var2 = var10000;
      return new l(this.c, var2, this.f, var1, this.a, this.g, this.c, this.a, this.b);
   }

   public l a(e var1, a var2) {
      k var3 = this.b;
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sp9rmysgguu7c","bHWI7Q6xHqTfe729rYohczPg/sxlAQsYh7OmMAoNOPM=",4920710700848296595,-1209577621881965359,8052263183258065847,-2257851989329920025>()) {
            case -2097907502:
               if (!var3.e().equals(var2)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s52endtp5z8ub","xYT8oYpkshvRjftOKCq+UsaFil/1TiTFYsngrsuNt+g=",1911878009697959148,525610784634173727,-8319696608141168539,8464779186557852649>()) {
                     case 1129939085:
                        var3 = null;
                        switch ((int)com.yiyiaddon.m.b.a<"s1x0vytvkgmdwf","FxYu82+2WMKEk3/OPWW1iS4GLMI1QpKA6S4/yuMulEs=",-5385317098492262933,-2428421693111408513,8860696636119424326,1002552708499153146>()) {
                           case -1194706028:
                              return new l(this.c, this.fD, this.f, this.a, var1, var2, this.c, this.a, var3);
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return new l(this.c, this.fD, this.f, this.a, var1, var2, this.c, this.a, var3);
   }

   public l a(k var1, e var2) {
      Objects.requireNonNull(
         var1,
         (String)com.yiyiaddon.m.b.a<"s2l6v2n5a8o3df","X8BEArB2CpHgYLp1v+/Lhwkm+2CbhCGmVY4IPVIRVgIGlv+W10tafpNYVJkVDr9GVivt0fLpsFI=",1942581109850072209,-2863580453434054415,-6745222910942213621,-1873481426422216407>()
      );
      return new l(this.c, this.fD, this.f, this.a, var2, var1.e(), var1.b(), this.a, var1);
   }

   public l a(n var1) {
      return new l(this.c, this.fD, this.f, this.a, this.a, this.g, this.c, var1, this.b);
   }

   public int bk() {
      return this.fD;
   }

   public a g() {
      return this.f;
   }

   public a e() {
      return this.g;
   }

   public c f() {
      return this.c;
   }

   public k a() {
      return this.b;
   }
}
