package com.yiyiaddon.e.m;

import com.yiyiaddon.d.a.c;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.m.b;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String qB = "autorespawn";
   private static final String qC = (String)b.a<"s2efo3epxgtsgw","pdNPbNK/Sr5vaGJ5QWpjwo4Jr33j3Q/95mpLb3Sz",3575750766882370209,6084387243080319021,-3466619767315009547,-8715389188599749325>();
   private final Minecraft U = Minecraft.getInstance();

   @Override
   public String w() {
      return (String)b.a<"s2efo3epxgtsgw","pdNPbNK/Sr5vaGJ5QWpjwo4Jr33j3Q/95mpLb3Sz",3575750766882370209,6084387243080319021,-3466619767315009547,-8715389188599749325>();
   }

   public a() {
      super(
         (String)b.a<"s2h2xxttodef7d","0jpLnxfuc4+b7kdajJPmBg1fkgq13zy5kWGR4aEu5YRbWcMLnBLPrNv444zS9DBlX98=",4370778257450355155,-7954129815692145015,2412133626985663094,-6422298740135895866>(),
         (String)b.a<"s2k4576wz82egc","LH5h1py7QDffzTCu8JtV8e1P/y6zBy+TPDKT9wSwE/SF6ghs",2114282012389982917,5626909898518506280,-3943698646459417230,-5407894038963130883>(),
         (String)b.a<"s1uc2z4riqrrvp","tBlBs2ArdVPMF6HIMZ96JjsxCDEMv3+b+37IvCpW96ZtG9Jl/CNisSizpaHcGcc+",-2979846607273911673,3406204332632453045,-3558504645762148358,494757154666023092>(),
         (String)b.a<"sw80f3l6w819i","E4/wb6H8Xo1pFZdPLxa6/mlOEJMfjUiZh1aCfXsl2VMoAlSCgnZpXpMW2WaK6ksLkmi+dyLwBS2Fjq1Mt3T6m/7V",7050511833123805539,3167197495869753578,1172096400610833050,-1283436982882601596>()
      );
   }

   @Override
   public int i() {
      return 10;
   }

   @Override
   public boolean h() {
      return true;
   }

   @Override
   public Set<c> c() {
      return Set.of(c.TICK);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 != null) {
         switch ((int)b.a<"s2l46inw40hpvd","wHI5mcYjnrousIZ9E8B3w2duH5Ttukbp3/6rC+9V0e0=",8546349581376218805,-4806734294137850604,-6823572606502816600,-8392892347628309896>()) {
            case -1157573054:
               if (var1.a() == c.TICK) {
                  if (this.U.player != null) {
                     switch ((int)b.a<"s39n07eno4f45n","xEQoMQl4e0T0YQFMhJDfmSNHfiHXinlX20cLNfaIK98=",2845386397893589186,-3337647566086699540,-4707684841672186124,6554816264414460114>()) {
                        case -2004211612:
                           if (this.U.level != null) {
                              if (!this.U.player.isDeadOrDying()) {
                                 switch ((int)b.a<"s3p1dn87ykqsto","xijwkHamZ5VRJqAyoYxefl5FLE7aIlx4Jnp4r/QoJPk=",2230446244634521131,4634786877446910833,2237464960899924771,-2249293936734541168>()) {
                                    case -1299225838:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              this.U.player.respawn();
                              return;
                           }

                           switch ((int)b.a<"s3gsu8r49e2mya","aukpwOo0yE091QH2pTkMr8g9MXPUU6MjmR22XEvyQko=",-7216740975843541024,-3709005413618572976,7956969208104485358,-2932833328588701011>()) {
                              case -1567820839:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)b.a<"su47zyoifrfyx","cUBGAznnEv+fLeCnM054UQbDZgsoe6Vg4JJYAIcsipM=",-7209323262347325636,-4035194549839536136,-3661670175477670717,5522261619105639130>()) {
                     case 121837132:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public List<String> f() {
      return List.of();
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.m.a.a(this);
   }
}
