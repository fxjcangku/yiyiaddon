package com.yiyiaddon.e.i.b;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public final class b {
   private static final Map<a, Set<a>> G = l();
   private final Map<a, Runnable> H = new EnumMap<>(a.class);
   private final Consumer<c> b;
   private final Consumer<RuntimeException> c;
   private a a = com.yiyiaddon.e.i.b.a.IDLE;
   private long q;
   private long r;

   public b(Consumer<c> var1, Consumer<RuntimeException> var2) {
      this.b = Objects.requireNonNull(
         var1,
         (String)com.yiyiaddon.m.b.a<"s2ye95m35ved8u","gAh3AikmXIeTuCz+Nb627V8lMRfQQFR40MRASZOfEgwvlTS0j0RFAnH0/bwUG/GTRhK8RAZqM/xjmMDnpG5S6w==",7323896924804176553,-6051007810787258269,2816701404685533031,1777781765602844763>()
      );
      this.c = Objects.requireNonNull(
         var2,
         (String)com.yiyiaddon.m.b.a<"s1lkmwazovm4zc","llbA3GoaDNFWaliKSkCyjZ6ZVDHEi1wygb/jJ0fpSMigxD2eVWbA6CxVHeUN3uY24/sYug==",-7978160056485874063,-16965639380406055,5739072433571766826,529322508310733043>()
      );
   }

   public void a(a var1, Runnable var2) {
      Objects.requireNonNull(
         var1,
         (String)com.yiyiaddon.m.b.a<"s816surhxx4i1","zdK6TmpYlF4h70O78YM4lTaDzuhjWtBwuMvHR47GaKn4+99rSBY=",9190431806709562107,279593249544769454,-7216373571143355320,4158222901342101988>()
      );
      Objects.requireNonNull(
         var2,
         (String)com.yiyiaddon.m.b.a<"s3nnx8c3sae9mq","YPdjotW9sA1FZ88UV5mbfHsPIH5dRLW8I5j4BVCeAtfmgJ1SjhUM/ISQ",1682793514795835609,-825986356327057514,6341977093034091105,-589661670418541475>()
      );
      if (var1 == com.yiyiaddon.e.i.b.a.IDLE) {
         switch ((int)com.yiyiaddon.m.b.a<"s2olvg4embe3dm","M8xHsv9YGp6osSduKvL+QJ7rOFDyfcmE5ZqIm8mznQc=",8203529034012432294,3587896438777842835,-7042319033077606698,5559913260250984177>()) {
            case 1076796160:
               throw new IllegalArgumentException(
                  (String)com.yiyiaddon.m.b.a<"s2ps2memrpe8vb","gEccR88rErnMxR52maWYqmUJ3GePV0e9iB7867qxwuAYq9SlJw4SRVi1tri+MST3UH0=",-835905454944331182,-4754553475902569765,9203348370680052910,8640534499742699269>()
               );
            default:
               throw null;
         }
      } else if (this.H.putIfAbsent(var1, var2) != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sw79ynaahx6ac","KMrDnzUzQnq242Py4KO1LMG5w1uwIAr0QoBDiRUzrhE=",-1670495191599249331,2022353193329228856,5097384275768429968,1816083319314773072>()) {
            case 1572290770:
               throw new IllegalArgumentException(var1 + "");
            default:
               throw null;
         }
      }
   }

   public void aR() {
      this.a(
         com.yiyiaddon.e.i.b.a.START,
         (String)com.yiyiaddon.m.b.a<"s1og777xkt73am","Ls50NnTG9eZyicQrERIOyi7SV/7V9KC3c63RW1Q2S6Qm75qM",4516135380105844548,7412570460965819575,-1979699357401320205,-1386019455384760869>()
      );
   }

   public void a(a var1, String var2) {
      Objects.requireNonNull(
         var1,
         (String)com.yiyiaddon.m.b.a<"s2fh3jo9aiob2u","hgOGrKsesQdOXsE0I+FlPor1MMdX5ubzXt7NJKrQMUNYTYpJ4Mi98RgXWjx+Ew==",-1466729981731862395,6481653454806991855,-1346568015887599085,-68181181073816454>()
      );
      if (var1 == this.a) {
         switch ((int)com.yiyiaddon.m.b.a<"s3lx9myww1y0s1","M/Mme4x4BaTP6WNTBrHoVsN9KqFhrad3FDg5rQ7oPvg=",-3110225791152114969,-2398038051896780800,3706036139465424250,-1005933662984803143>()) {
            case -770281771:
               throw new IllegalStateException(this.a + "");
            default:
               throw null;
         }
      } else {
         if (var1 != com.yiyiaddon.e.i.b.a.IDLE) {
            switch ((int)com.yiyiaddon.m.b.a<"sh865zl0eiyeo","DA0Mq2thj4AXg4vbWlq8KnNJQdpzRXZMJ1QCWJxgLN4=",5511753960717786941,5540253462452966015,-6215214145257694120,-8765048468261817218>()) {
               case -1716960812:
                  if (!G.get(this.a).contains(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sx42cjij0sbn9","4wUtp9i+RDUJ8OYAEQ1A52VDH1wxnXYdFYOjUSY1o38=",-1973497390656036453,5153769566203655280,3723804961845929811,-143257490262709880>()) {
                        case 1183003009:
                           throw new IllegalStateException("" + this.a + var1);
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         a var3 = this.a;
         this.a = var1;
         this.r = this.q;
         this.b.accept(new c(var3, var1, this.q, var2));
      }
   }

   public void ae() {
      this.q++;
      if (this.a != com.yiyiaddon.e.i.b.a.IDLE) {
         Runnable var1 = this.H.get(this.a);
         if (var1 == null) {
            this.a(new IllegalStateException(this.a + ""));
         } else {
            try {
               var1.run();
            } catch (RuntimeException var3) {
               this.a(var3);
            }
         }
      }
   }

   public void Z(String var1) {
      this.a(com.yiyiaddon.e.i.b.a.IDLE, var1);
   }

   private void a(RuntimeException var1) {
      if (this.a != com.yiyiaddon.e.i.b.a.ERROR) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"stuauiyufd2e0","wXM8kQFxAnS19VP+HI7l7KUKJCHlt38drvp4PfyST9o=",7737798846523276673,181468653344833197,3593019248310170844,2285724834167022261>()) {
            case -54600715:
               this.a(com.yiyiaddon.e.i.b.a.ERROR, var1.getMessage());
               switch ((int)com.yiyiaddon.m.b.a<"s32le4jh4f7h2u","sBtsBWP1W6r8Od8VVJylzmbJA/MVMGPe5CkqEL4RHm4=",1226400189104504206,-1917005411114292883,-250644326705373045,6952333488545892608>()) {
                  case -72561120:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.c.accept(var1);
   }

   private static Map<a, Set<a>> l() {
      EnumMap var0 = new EnumMap<>(a.class);
      a[] var1 = com.yiyiaddon.e.i.b.a.values();
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3nxeizsouv1ry","eXtFQi8PKo5SIuWfoutYETHUy9Uuy64OpNyhvcevo2U=",-2714285172460120911,-6416126902815100082,-2876336544097791938,6936792954689291044>()) {
         case -2004911248:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s33odjivyhbz95","9v9bf+5Dus3YLrXANmgqeq1JiSMZMTL1Crr/Wks2YZ4=",3523004139709347237,-8687068161873108882,-7654895411774591069,2600412074417260656>()) {
                  case -108723506:
                     a var4 = var1[var3];
                     var0.put(var4, EnumSet.of(com.yiyiaddon.e.i.b.a.ERROR));
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1p5ymr7iar5al","cJZVcFc90GwowM3JIddfeW3H1+GTJEhMRgYIVIcZNOY=",1945757177221674145,-5388317466925333083,-3720869825414314213,4542976600501754277>()) {
                        case 715454400:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var0.put(com.yiyiaddon.e.i.b.a.IDLE, EnumSet.of(com.yiyiaddon.e.i.b.a.START));
            a(var0, com.yiyiaddon.e.i.b.a.START, com.yiyiaddon.e.i.b.a.SEARCH_VILLAGER, com.yiyiaddon.e.i.b.a.FINISH);
            a(var0, com.yiyiaddon.e.i.b.a.SEARCH_VILLAGER, com.yiyiaddon.e.i.b.a.SELECT_TARGET_VILLAGER);
            a(var0, com.yiyiaddon.e.i.b.a.SELECT_TARGET_VILLAGER, com.yiyiaddon.e.i.b.a.MOVE_TO_VILLAGER, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.MOVE_TO_VILLAGER, com.yiyiaddon.e.i.b.a.FIND_LECTERN_POSITION, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.FIND_LECTERN_POSITION, com.yiyiaddon.e.i.b.a.MOVE_TO_STAND_POSITION, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(
               var0,
               com.yiyiaddon.e.i.b.a.MOVE_TO_STAND_POSITION,
               com.yiyiaddon.e.i.b.a.BREAK_OBSTACLE,
               com.yiyiaddon.e.i.b.a.PLACE_LECTERN,
               com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE
            );
            a(var0, com.yiyiaddon.e.i.b.a.BREAK_OBSTACLE, com.yiyiaddon.e.i.b.a.PLACE_LECTERN, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(
               var0,
               com.yiyiaddon.e.i.b.a.PLACE_LECTERN,
               com.yiyiaddon.e.i.b.a.BREAK_OBSTACLE,
               com.yiyiaddon.e.i.b.a.WAIT_PROFESSION,
               com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE
            );
            a(
               var0,
               com.yiyiaddon.e.i.b.a.WAIT_PROFESSION,
               com.yiyiaddon.e.i.b.a.OPEN_TRADE,
               com.yiyiaddon.e.i.b.a.RESET,
               com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE
            );
            a(
               var0,
               com.yiyiaddon.e.i.b.a.OPEN_TRADE,
               com.yiyiaddon.e.i.b.a.WAIT_TRADE_SCREEN,
               com.yiyiaddon.e.i.b.a.RESET,
               com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE
            );
            a(
               var0,
               com.yiyiaddon.e.i.b.a.WAIT_TRADE_SCREEN,
               com.yiyiaddon.e.i.b.a.READ_TRADES,
               com.yiyiaddon.e.i.b.a.RESET,
               com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE
            );
            a(
               var0,
               com.yiyiaddon.e.i.b.a.READ_TRADES,
               com.yiyiaddon.e.i.b.a.CHECK_ENCHANTMENT,
               com.yiyiaddon.e.i.b.a.RESET,
               com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE
            );
            a(
               var0,
               com.yiyiaddon.e.i.b.a.CHECK_ENCHANTMENT,
               com.yiyiaddon.e.i.b.a.RESET,
               com.yiyiaddon.e.i.b.a.SUCCESS_FOUND,
               com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE
            );
            a(var0, com.yiyiaddon.e.i.b.a.RESET, com.yiyiaddon.e.i.b.a.BREAK_LECTERN, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.BREAK_LECTERN, com.yiyiaddon.e.i.b.a.WAIT_UNEMPLOYED, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.WAIT_UNEMPLOYED, com.yiyiaddon.e.i.b.a.FIND_LECTERN_POSITION, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.SUCCESS_FOUND, com.yiyiaddon.e.i.b.a.TRADE_PROCESS, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.TRADE_PROCESS, com.yiyiaddon.e.i.b.a.SELECT_TRADE, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.SELECT_TRADE, com.yiyiaddon.e.i.b.a.WAIT_TRADE_SYNC, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.WAIT_TRADE_SYNC, com.yiyiaddon.e.i.b.a.TAKE_TRADE_OUTPUT, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.TAKE_TRADE_OUTPUT, com.yiyiaddon.e.i.b.a.VERIFY_PURCHASE, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.VERIFY_PURCHASE, com.yiyiaddon.e.i.b.a.COMPLETE_TARGET, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE);
            a(var0, com.yiyiaddon.e.i.b.a.COMPLETE_TARGET, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE, com.yiyiaddon.e.i.b.a.FINISH);
            a(var0, com.yiyiaddon.e.i.b.a.END_VILLAGER_CYCLE, com.yiyiaddon.e.i.b.a.SEARCH_VILLAGER);
            return Map.copyOf(var0);
         default:
            throw null;
      }
   }

   private static void a(Map<a, Set<a>> var0, a var1, a... var2) {
      ((Set)var0.get(var1)).addAll(EnumSet.of(var2[0], var2));
   }

   public a b() {
      return this.a;
   }

   public long i() {
      return this.q;
   }

   public long j() {
      return this.q - this.r;
   }
}
