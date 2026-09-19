package com.yiyiaddon.j.b;

import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

public final class c {
   private final Set<com.yiyiaddon.g.c.b> aH = new LinkedHashSet<>();

   public Path k() {
      return com.yiyiaddon.i.f.a.g();
   }

   public void C() {
      this.aH.clear();
      Iterator var1 = com.yiyiaddon.j.a.b(this.k()).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s8ctfr63eul8r","q4QulMB98wdiL8LDkOtEFxkwzRbg+2BwigWWABvbxyY=",-2594980805082828812,6116885635878588056,-5769371835821765179,7740066649466264028>()) {
         case 1833916180:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1eecyle9o5eun","hI8nfaPlMeZXZ1rRKK9oJAjpeWWZ7v77/bKznRhpFqY=",1168702648744753480,7173706916100158166,2286385493428890419,4993276829981758875>()) {
                  case 1086388738:
                     JsonObject var2 = (JsonObject)var1.next();
                     com.yiyiaddon.g.c.b var3 = com.yiyiaddon.g.c.b.a(var2);
                     if (var3 != null) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s344lsa4hhcm2g","1A8Aj8rKWyBGQh4o6X2CRM1I03u69cuWuFbE5d98Exk=",-9149912901457292021,2883697414187071294,9212594331630394906,4798879152578819864>()) {
                           case 105140504:
                              this.aH.add(var3);
                              switch ((int)com.yiyiaddon.m.b.a<"s1f92guoej21x3","JYHhi7Qt7N1aP59eUR9dks1OW7/A4MDvO+iTuehoFaI=",-3234917298013284616,4713152056147453331,9005984903893907960,-8020889179283725236>()) {
                                 case -305765404:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"sj3t97j8d78cg","28OrxZWYnDvSxlZnAp9pnWZJrc5sicDDP7jeREpAkjk=",-956368952437869385,6243622183506015826,1820822689081547912,7670770769627157187>()) {
                        case -469414623:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   public String c(com.yiyiaddon.g.c.b var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5vh1jg54l0ia","o8M0Hmo/XiUKFUh65VfjGWwLEzVBPwDGfbl2mxUlT5c=",3022160102221316537,4263843200793462467,-9143333645577633204,554185138760713137>()) {
            case -1763535001:
               return null;
            default:
               throw null;
         }
      } else if (this.aH.contains(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nu24hzwtbse7","IL+ol+PjwwedsUkBj4vfhCJqgLZFTzhjogsy2evxYjU=",-5262300196097616364,4998837076983713420,7378481991023341065,-6873996711409265682>()) {
            case 479992005:
               return null;
            default:
               throw null;
         }
      } else {
         Path var2 = com.yiyiaddon.j.a.a(this.k(), this.d(var1));
         if (!com.yiyiaddon.j.a.a(var2, var1.f())) {
            switch ((int)com.yiyiaddon.m.b.a<"s12nxpcisnd7e","rnxb2GlDrs1OOl6ruXMPBYUcqFt1YjDFc4Ksy/OMZDI=",-689949185345072393,6247006889772565708,-3710987349814564635,-3206359352126564164>()) {
               case -390963586:
                  return null;
               default:
                  throw null;
            }
         } else {
            this.aH.add(var1);
            return var2.getFileName().toString();
         }
      }
   }

   public boolean a(com.yiyiaddon.g.c.b var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s32gofvz4hu1pp","gBhyzgk0VIuIZTrugdlloFTXxTtW0BeP43l+ObaYBv8=",-7011749327839723931,-6280757396230977325,8023579968147435077,-7528582076544166782>()) {
            case 186396721:
               return false;
            default:
               throw null;
         }
      } else if (!this.aH.remove(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s37onjpvor3jqy","3PJCh2qXTBhqsy9//EpGsfROtzW+z9T15QmyH/Rr6Sg=",-9088986702893221655,6239578534428110426,-1261872716507091071,4962884386341292420>()) {
            case -1848521962:
               return false;
            default:
               throw null;
         }
      } else if (!this.fy()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nafokz9d4m3d","3De7uPjXpL/f6qOXGnFiwdD0VsdzThPNpzVls7il2rM=",-7138401719325197319,-3395340226988850076,2401416937607304516,-2175500904921556161>()) {
            case -153475723:
               this.aH.add(var1);
               return false;
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   public boolean fx() {
      LinkedHashSet var1 = new LinkedHashSet<>(this.aH);
      this.aH.clear();
      if (this.fy()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2a8a55xs3evo5","NfKmnMt2YCwtMEzpYVXEK2sNgun9OR8rwooNhRwPocw=",-5709686163901161659,-5484980870320297784,2632337020767025562,-5606907670414591508>()) {
            case 48625115:
               return true;
            default:
               throw null;
         }
      } else {
         this.aH.addAll(var1);
         return false;
      }
   }

   public Set<com.yiyiaddon.g.c.b> A() {
      return new LinkedHashSet<>(this.aH);
   }

   public com.yiyiaddon.g.c.b a(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s158usfym7oi1q","Fe4eS1MNSccdjMDgvdVYWjyaehaUcBNr6JVoZ4eku1w=",7907099853891849102,171378702750069628,-6341216647266276877,2692370174323288445>()) {
            case 197621341:
               return null;
            default:
               throw null;
         }
      } else {
         Iterator var2 = this.aH.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s7lddnb25axgk","x5ubGPD8y147cUCgjNnBjeg7TEIp9mRanmtG96AOWj4=",1970888907643621567,-4863000102510116795,3677173903990441391,7449408159866786208>()) {
            case 1065777513:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3dc6suirvda06","jpKauSKIpfzf+O0S9MeN9jki+YgcP0bpDVETZw0ncSY=",-6495231451081072760,431870377193931746,-1153448019711864319,-2328862060282378792>()) {
                     case -502920702:
                        com.yiyiaddon.g.c.b var3 = (com.yiyiaddon.g.c.b)var2.next();
                        if (var3.dF().equals(var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3dohag5s5on9m","HBuFkVCBCSYqUNvqzTHJUSnfQXyFZOBeg66ZQaGfKco=",3501231109181072531,-4564205593745028360,7246042897734485547,1752091217025003136>()) {
                              case -303174322:
                                 return var3;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sl0ttc08i6fqg","Gc4HQc18SMmPyyYAY39sWLH5VyGbKp4uQg5oct+53uw=",-209242580932782756,-2598973221121255867,8821387183195879684,6329421831521313777>()) {
                           case -1544218540:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return null;
            default:
               throw null;
         }
      }
   }

   public int d(Predicate<String> var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2rv84x05tw3sf","DNYsHeMbBEufL+iGXp3aeSru/cudSkuvojWqVcktGVg=",-830947608311531103,570567942049441463,4163025041952106710,5215082211287440031>()) {
            case -582126169:
               return 0;
            default:
               throw null;
         }
      } else {
         LinkedHashSet var2 = new LinkedHashSet();
         Iterator var3 = this.aH.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2ab8z2tyb5t7y","89T7X1vmLCw+BTcN5IK4Vif2kwIHq8rfHE6vHLMbIr8=",-5213009045413537012,2008525961899956235,8487336766788167817,3291790679293173664>()) {
            case -747171610:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3rcq3vlfhkkvy","8xInP+LTfiNzR6mE99/0kDNnt6BlZji9ZfN+x+TQ3+A=",-5752294579630764779,-5793764057721005549,-7254866403511612194,-791009199078072484>()) {
                     case 1802493245:
                        com.yiyiaddon.g.c.b var4 = (com.yiyiaddon.g.c.b)var3.next();
                        if (!var1.test(var4.fU())) {
                           label35:
                           switch ((int)com.yiyiaddon.m.b.a<"sk3d6td55ydf8","srU7sTlR+4REsfpB9iTOHQjQ5azmSdziNZrORUeCTOw=",-8244536553488293432,-6871413635035304261,1921957653142720130,4296849911792487448>()) {
                              case 1543985101:
                                 var2.add(var4);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1hf4lp8kwkrkf","XLBqTgcS5z9CSR6CXbDnkFkSkt76r3fAQLvyZ/qmm7g=",8693809282167463958,7008279660264442515,294698246173363037,787836327125183481>()) {
                                    case -1536838240:
                                       break label35;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s309pozmz0qk3e","zvaW8I533OUcNKxKXdqOkMZWfR//0a/56DolJ76f5qM=",6894823247576874358,-6089320425854152737,7612144438904936260,2917079345582784593>()) {
                           case 1274172598:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var2.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sjdh4n932aexr","ORZ7xUmcRwWSx2ArnJZ40bCY6hzB1trRNB+Lzm7NHcU=",1308712143516388727,-8098106219840966755,-1172214344407533360,480020236015265727>()) {
                     case -2101050733:
                        return 0;
                     default:
                        throw null;
                  }
               }

               this.aH.removeAll(var2);
               this.fy();
               return var2.size();
            default:
               throw null;
         }
      }
   }

   public boolean dt() {
      if (!this.aH.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s39zm619p7td9","AGJYyrtPf7FGcaFMsDe4bMoG5YDZLu8Aq4+YBqyzV4M=",644391644708226181,-2703112343816126627,-3697891012347398417,2274657726574823784>()) {
            case -1429033060:
               switch ((int)com.yiyiaddon.m.b.a<"sa1adx46pws38","BYiejb/+uOZK5Qj/RuAaR7et50qL46H4pd6vR0Rg9mE=",-7664637566871820206,-1026581399085575670,-8862161251039373227,-8525029226607016702>()) {
                  case -858559443:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2xjyx5kd2r1ty","fUcXvYvvL3w0oIFT/W++i5mQ1wHtRim0xUqbnuXjdUw=",-5452451442398647593,6185424030947348053,312735422938572610,1015288327264762332>()) {
            case 894274322:
               return false;
            default:
               throw null;
         }
      }
   }

   public int a() {
      return this.aH.size();
   }

   private boolean fy() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.aH.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"szkn5c0ud1gkw","/5tFM+vfXxTNGVvAOt/GS4rleyJKOn9QXXJzIEm2U2I=",-7862086995998631184,8382795168751818696,-1656256942358015430,-1729749955779898962>()) {
         case -1540355890:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1yzz4pw1fmta2","Cx46YwKIE27OtVVnNUu918uhgC92uHfyWG1mYiI2c3I=",2599663885004980234,7258365747990613883,-5846554896579798939,-311353779951147546>()) {
                  case 1466307153:
                     com.yiyiaddon.g.c.b var3 = (com.yiyiaddon.g.c.b)var2.next();
                     var1.add(new com.yiyiaddon.j.a.a(this.d(var3), var3.f()));
                     switch ((int)com.yiyiaddon.m.b.a<"s3q2fyqbwz5q73","IFtcyVDrLCR0N0kQrkZjyVpt1lNlmMEDMENjNHZaOaU=",2865308167005861017,452715966135789182,-2831267878166901416,-6553962347413108744>()) {
                        case 1686610862:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return com.yiyiaddon.j.a.a(
               this.k(),
               var1,
               (String)com.yiyiaddon.m.b.a<"sifbppgjltj75","ZaCaD15ohIX7WPramYHtAcriZkk6dKEO8CvaQdjsEQ417KpS+TvrngxC+Q0=",-1173202242077260175,8152699179564549373,-2523835917212110510,7760419120897026243>()
            );
         default:
            throw null;
      }
   }

   private String d(com.yiyiaddon.g.c.b var1) {
      return com.yiyiaddon.m.a.B(var1.m(), var1.fU());
   }
}
