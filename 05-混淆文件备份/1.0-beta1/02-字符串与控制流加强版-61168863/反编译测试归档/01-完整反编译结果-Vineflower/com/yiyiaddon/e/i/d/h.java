package com.yiyiaddon.e.i.d;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class h {
   private final List<b> aZ;
   private final int fw;

   public h(List<b> var1) {
      List var2 = List.copyOf(
         Objects.requireNonNull(
            var1,
            (String)com.yiyiaddon.m.b.a<"s7tvffota5ldi","CpmlMj12SwKU5gV/ry2EwqYmCeT+dXwghdM/YDS4zIiHnf1mQzi5IIpnlk1gYKzh6a21IRYPZYbix1ly12g=",7326240531304966521,8754385934195386741,1838727114587027018,-7260858569909374088>()
         )
      );
      if (var2.isEmpty()) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s32x7igcd4cehh","sEdPcwxnRDhyIBUg5D3XlxRbFCHZZ0VP31bVEFy6GmOPA0ykmj4NZa+Vm2PIfsSU",-2479609423928323481,-2965574851009432210,-4579869810401081244,5541090244466544048>()
         );
      }

      this.aZ = new ArrayList<>(var2);
      this.fw = var2.size();
   }

   public Optional<b> g() {
      return this.aZ
         .stream()
         .filter(
            var0 -> {
               if (!var0.br()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1uzqlblpfivul","8ylFRCOc0EpRiAbQ5fAVeu+W/ErhhY2aBgxCDcIxjBo=",-5931937488577515214,-7843344197000839117,5119526328183486141,-3839839681314850657>()) {
                     case -1128673629:
                        switch ((int)com.yiyiaddon.m.b.a<"s1la4qnmhpsjnx","FiMbsG015Df3f7xYPUKwmsSSjqXzt/2lblWN9w4Lgf8=",-6225677296010731362,-1506904505837791264,6340164242184568019,-174214131525222048>()) {
                           case -1932922358:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s18qal7ddmdvkh","8G33rLLjuc8tam/GDKVrC2vHjZuLfMYTzkE7u0q0wDw=",7769768209169478610,2078404250810915723,4218982671249896213,-5843517940571779624>()) {
                     case -1008135340:
                        return false;
                     default:
                        throw null;
                  }
               }
            }
         )
         .findFirst();
   }

   public List<b> ag() {
      return this.aZ
         .stream()
         .filter(
            var0 -> {
               if (!var0.br()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2fdt3g0v5u4p","JvaEbJrVaUy8k5kRKCI+lWSmosZgJAdtzg4coBNhUJw=",4718230526378487407,5069105355855713061,4410198947325778368,7509090667960405638>()) {
                     case 1173563081:
                        switch ((int)com.yiyiaddon.m.b.a<"s2o2ybhmy162av","rjBGyY+NbEmVJLPWFNK2kOHgoMDTmzbRzppf4T/Wdb8=",-4420727790395670297,-3251666464630099238,-5024746033840028693,4918726115918932975>()) {
                           case -754864146:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2jmciueks66f1","6U98iqKY+KKWMfB46zR3s33I11ZKXoc2n/76mdnlIjo=",166275558732137662,569116597963274389,2760051458389194926,436559179053077656>()) {
                     case -39294716:
                        return false;
                     default:
                        throw null;
                  }
               }
            }
         )
         .toList();
   }

   public void b(b var1) {
      this.a(var1, false);
   }

   public void a(b var1, boolean var2) {
      Objects.requireNonNull(
         var1,
         (String)com.yiyiaddon.m.b.a<"sxxlr1iie2qad","oEFoG2QbzPMqKLxEnmq/Z8sR7R8994xV3AI4Zdy+TFTLhesxa+4zSA==",-427194693382148958,-1542086348550918341,107327706470571483,6875915853009978623>()
      );
      int var3 = this.a(var1);
      if (var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s25gpxui1hz9xh","n9D5bzNRDlR9fCoIeGDZmf2dPGf+YI32n7gmClrUv7I=",5706777149647372513,-7591327050191186527,-405505000029485646,5834717689034672872>()) {
            case 671199783:
               this.aZ.remove(var3);
               switch ((int)com.yiyiaddon.m.b.a<"s3u85xheu6hh56","v2W9xTbaX8fkqCqYigXAS+OaK48Zqi0ueW1hLwMymVM=",-1411526038232957404,-7127728829040389402,-6575070382661295928,-7388392602809676715>()) {
                  case -760389493:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.aZ.set(var3, this.aZ.get(var3).b());
         switch ((int)com.yiyiaddon.m.b.a<"s3gi3s3sizpbr0","ko6Hjwp906gMMogASLU7zIFMUWzubbchRsUUyRg+aSo=",1994618219676551729,-1264938927377976573,5233464483043544410,-5017961676474863291>()) {
            case 593402061:
               return;
            default:
               throw null;
         }
      }
   }

   public boolean b(b var1) {
      return this.aZ.get(this.a(var1)).br();
   }

   public boolean bu() {
      return this.g().isEmpty();
   }

   public int bd() {
      int var1 = this.fw - this.aZ.size();
      return var1 + (int)this.aZ.stream().filter(b::br).count();
   }

   private int a(b var1) {
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s17qbvo6keov0h","Dy8aog9oNhqXE0kGxj7aY9l6bmBD0vmQEPuJc2uqVMw=",8199376996508807693,3283476958541098222,-8659135041676184907,-6921460752160965403>()) {
         case 1062584346:
            while (var2 < this.aZ.size()) {
               switch ((int)com.yiyiaddon.m.b.a<"s216k0gt3esimg","jLcKcMfG4DqUFvH1akrcqw59zJ33hCR0ShXELORgpz0=",-8071545230202605754,9178888687853932340,-7107572903650713337,1350357134170585465>()) {
                  case 1171766908:
                     b var3 = this.aZ.get(var2);
                     if (var3.bs().equals(var1.bs())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1fqmaovymy4z0","vmZ/M18GEUO9FdyaIv0pY2+vu+VzTsFJG6trUf58w2k=",281638912662026910,-2993537966882219726,3552465871489736662,-3057726830644375729>()) {
                           case -1432863987:
                              if (var3.ai() == var1.ai()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3l5ioy14b235n","rAXj/blDwWrasJ1Hn5RuI0wpEO1wk9g6xtmHXhLvc/o=",4642106970003348621,1012442014205234824,4328213743666335110,969264033043203432>()) {
                                    case 2053292515:
                                       return var2;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"s12ollx8z5c7jl","w0sM+qB0Fidoe/DZfHFpq3kptXxycCZVNRNPArbFKAk=",8324112237584015262,5631511764409094416,-3853561862628067103,-2634664052397887242>()) {
                        case 1631863851:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            throw new IllegalArgumentException(
               (String)com.yiyiaddon.m.b.a<"s2l8tvsf5rypku","V6FApyw2TBndv2L8Ep/VunTaT5c+F4hoEF7JgC04xUNaF7zz1Dnob3/uW76PjA==",5536667128362643956,-7998911887364139255,7782649448364960110,1368373566535059314>()
            );
         default:
            throw null;
      }
   }
}
