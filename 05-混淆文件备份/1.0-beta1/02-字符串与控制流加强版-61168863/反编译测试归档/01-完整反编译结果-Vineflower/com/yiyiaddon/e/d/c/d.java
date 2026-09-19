package com.yiyiaddon.e.d.c;

import net.minecraft.client.Minecraft;

public final class d {
   private final Minecraft n;
   private final com.yiyiaddon.e.d.a.a b;
   private final Runnable a;
   private int cu;
   private boolean L;

   public d(Minecraft var1, com.yiyiaddon.e.d.a.a var2, Runnable var3) {
      this.n = var1;
      this.b = var2;
      this.a = var3;
   }

   public void f() {
      this.cu = 0;
      this.L = false;
   }

   public void aR() {
      this.cu = 0;
      this.L = true;
   }

   public void ae() {
      if (!this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"s38btmj41i5rid","+A0k8TV87q015dgZm61iit8e/rJXIlvTxUmXvgj9934=",-8609569185731702822,3348039083307203813,-8342181064808534374,5641572684583209543>()) {
            case 1828351883:
               return;
            default:
               throw null;
         }
      } else {
         this.cu++;
         if (this.cu >= this.b.ci) {
            switch ((int)com.yiyiaddon.m.b.a<"s21ptfh20bzzpl","3Sh5HF/8OvGovDYWP2C4fs4Civ00qRaOSxsXYfh48W0=",1920384285422026533,7986318054617084746,5655816487235027707,-1622664881811692338>()) {
               case -1131903862:
                  String var1 = this.b.co.trim();
                  if (!var1.isEmpty()) {
                     label40:
                     switch ((int)com.yiyiaddon.m.b.a<"s1n03t9frieuig","a6K6pJKQ2PDNUh0BdUitrgAdtJEZvsE/GA8ajT8yZ0I=",8534955839188589817,-4247141014420681880,-7599189896855738122,-4530072300023610863>()) {
                        case 1230651015:
                           if (var1.startsWith(
                              (String)com.yiyiaddon.m.b.a<"szvphgoqb498x","GNN/uQOQYlNBs0L0eizVIJ4AAQ98dCvWnqbkXwVA",6315106181571225596,-5112277512256976482,6613551208994552746,1429641487346118175>()
                           )) {
                              label37:
                              switch ((int)com.yiyiaddon.m.b.a<"s2x9ut734d45iw","Detpxr1hq8GnWcqWlp2JUD3eNwE7igCEDMc9JLbHzdU=",9009303894109171434,3533626332236032345,4615868657190164384,-6382940247508284522>()) {
                                 case 1630169611:
                                    var1 = var1.substring(1);
                                    switch ((int)com.yiyiaddon.m.b.a<"srw47kutqv5j9","qEL/MMfcsPPpEP5mpOgXYP6YDcLWxvu1Mtp1/Q/SKQY=",8036393466042487750,-1825709296288323168,-7647804789804525738,-2234393136143111321>()) {
                                       case 1357467446:
                                          break label37;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.n.getConnection() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"sour3sqig5vvg","zSMjAIlsHIzK9uZKO8+9wDCY925oNSaNORZgIXdSB4w=",4704882085493675820,-6714086038143077632,2620901160432778826,7398387209780198871>()) {
                                 case 717462792:
                                    this.n.getConnection().sendCommand(var1);
                                    switch ((int)com.yiyiaddon.m.b.a<"s17kfj5mw5kvse","cSporVlmWjsSNXmWpT2i06MkWdNRG42prpeCF5gSlSI=",-5455793054438443507,3950495874263496251,-7384250871852988912,8975314679221791995>()) {
                                       case -1243473733:
                                          break label40;
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

                  this.L = false;
                  this.a.run();
                  switch ((int)com.yiyiaddon.m.b.a<"s1m6y918h5m35s","iZoD5zKzP2RhmdSbaerVjszx+pUgDWX1uhYjYa3rCdo=",-7181153724176093150,9077320944629574032,-8532929951169266095,7744984004851555667>()) {
                     case -186564278:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   public boolean isActive() {
      return this.L;
   }
}
