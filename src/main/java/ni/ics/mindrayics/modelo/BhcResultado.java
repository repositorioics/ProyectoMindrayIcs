package ni.ics.mindrayics.modelo;

import java.io.Serializable;
import java.util.Date;

public class BhcResultado implements Serializable{

    private Integer id;//1
    private String idMuestra;//2
    private String modoRecue;//3
    private String panelPrue;//4
    private String fecha;//5
    private String hora;//6
    private String postubo;//7
    private String comunicado;//8
    private String impreso;//9
    private String WBC;//10
    private String Neu_numeral;//11
    private String Linf_numeral;//12
    private String Mon_numeral;//13
    private String Eos_numeral;//14
    private String Bas_numeral;//15
    private String IMG_numeral;//16
    private String Neu_porcentaje;//17
    private String Linf_porcentaje;//18
    private String Mon_porcentaje;//19
    private String Eos_porcentaje;//20
    private String Bas_porcentaje;//21
    private String IMG_porcentaje;//22
    private String RBC;//23
    private String HGB;//24
    private String HCT;//25
    private String MCV;//25
    private String MCH;//27
    private String MCHC;//28
    private String RDW_CV;//29
    private String RDW_SD;//30
    private String PLT;//31
    private String MPV;//32
    private String PDW;//33
    private String PCT;//34
    private String P_LCC;//35
    private String P_LCR;//36
    private String NRBC_numeral;//37
    private String NRBC_porcentaje;//38
    private String WBC_BF;//39
    private String TC_BF_numeral;//40
    private String MN_numeral;//41
    private String MN_porcentaje;//42
    private String PMN_numeral;//43
    private String PMN_porcentaje;//44
    private String RBC_BF;//45
    private String ID_pacient;//46
    private String Nomb;//47
    private String Sexo;//48
    private String Fecha_nacim;//49
    private String Ed;//50
    private String Tipo_pac;//51
    private String Grupo_ref;//52
    private String Dpto;//53
    private String N_cama;//54
    private String Fech_extr;//55
    private String Hora_extr;//56
    private String Fecha_ext;//57
    private String Hora_entrega;//58
    private String Medico;//59
    private String Operador;//60
    private String Validado_por;//61
    private String Coments;//62
    private String Mensaje_WBC;//63
    private String Mensaje_RBC;//64
    private String Mensaje_PLT;//65
    private String PLT_I;//66
    private String WBC_D;//67
    private String TNC_D;//68
    private String WBC_N;//69
    private String TNC_N;//70
    private String HFC_numeral;//71
    private String HFC_porcentaje;//72
    private String IME_porcentaje;//73
    private String IME_numeral;//74
    private String H_NR_porcentaje;//75
    private String L_NR_porcentaje;//76
    private String NLR;//77
    private String PLR;//78
    private String InR_numeral;//79
    private String InR_porcentaje;//80
    private String Micro_numeral;//81
    private String Micro_porcentaje;//82
    private String Macro_numeral;//83
    private String Macro_porcentaje;//84
    private String PDW_SD;//85
    private String Eos_BF_numeral;//86
    private String Eos_BF_porcentaje;//87
    private String Neu_BF_numeral;//88
    private String Neu_BF_porcentaje;//89
    private String LY_BF_numeral;//90
    private String LY_BF_porcentaje;//91
    private String MO_BF_numeral;//92
    private String MO_BF_porcentaje;//93
    private String HF_BF_numeral;//94
    private String HF_BF_porcentaje;//95
    private String RBC_BF_I;//96
    private String Grupo_sang;//97
    private String ESR;//98
    private String Param_microscop;//99

    private String LYM_porcentaje;
    private String LYM_numeral;
    private String PLC_C;
    private String HNR_porcentaje;
    private String LNR_porcentaje;
    private String Mensaje;
    private String Nombre_equipo;
    private Date fec;
    private boolean procesoAutomatico;
    private String fechaPa;

    private String observacion;
    private String valido;
    private String proposito;
    private int ID_CONSECUTIVO;


    public BhcResultado(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(String idMuestra) {
        this.idMuestra = idMuestra;
    }

    public String getModoRecue() {
        return modoRecue;
    }

    public void setModoRecue(String modoRecue) {
        this.modoRecue = modoRecue;
    }

    public String getPanelPrue() {
        return panelPrue;
    }

    public void setPanelPrue(String panelPrue) {
        this.panelPrue = panelPrue;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPostubo() {
        return postubo;
    }

    public void setPostubo(String postubo) {
        this.postubo = postubo;
    }

    public String getComunicado() {
        return comunicado;
    }

    public void setComunicado(String comunicado) {
        this.comunicado = comunicado;
    }

    public String getImpreso() {
        return impreso;
    }

    public void setImpreso(String impreso) {
        this.impreso = impreso;
    }

    public String getWBC() {
        return WBC;
    }

    public void setWBC(String WBC) {
        this.WBC = WBC;
    }

    public String getNeu_numeral() {
        return Neu_numeral;
    }

    public void setNeu_numeral(String neu_numeral) {
        Neu_numeral = neu_numeral;
    }
    public String getLinf_numeral() {
        return Linf_numeral;
    }

    public void setLinf_numeral(String linf_numeral) {
        Linf_numeral = linf_numeral;
    }

    public String getMon_numeral() {
        return Mon_numeral;
    }

    public void setMon_numeral(String mon_numeral) {
        Mon_numeral = mon_numeral;
    }

    public String getEos_numeral() {
        return Eos_numeral;
    }

    public void setEos_numeral(String eos_numeral) {
        Eos_numeral = eos_numeral;
    }

    public String getBas_numeral() {
        return Bas_numeral;
    }

    public void setBas_numeral(String bas_numeral) {
        Bas_numeral = bas_numeral;
    }

    public String getIMG_numeral() {
        return IMG_numeral;
    }

    public void setIMG_numeral(String IMG_numeral) {
        this.IMG_numeral = IMG_numeral;
    }

    public String getNeu_porcentaje() {
        return Neu_porcentaje;
    }

    public void setNeu_porcentaje(String neu_porcentaje) {
        Neu_porcentaje = neu_porcentaje;
    }

    public String getLinf_porcentaje() {
        return Linf_porcentaje;
    }

    public void setLinf_porcentaje(String linf_porcentaje) {
        Linf_porcentaje = linf_porcentaje;
    }

    public String getMon_porcentaje() {
        return Mon_porcentaje;
    }

    public void setMon_porcentaje(String mon_porcentaje) {
        Mon_porcentaje = mon_porcentaje;
    }

    public String getEos_porcentaje() {
        return Eos_porcentaje;
    }

    public void setEos_porcentaje(String eos_porcentaje) {
        Eos_porcentaje = eos_porcentaje;
    }

    public String getBas_porcentaje() {
        return Bas_porcentaje;
    }

    public void setBas_porcentaje(String bas_porcentaje) {
        Bas_porcentaje = bas_porcentaje;
    }

    public String getIMG_porcentaje() {
        return IMG_porcentaje;
    }

    public void setIMG_porcentaje(String IMG_porcentaje) {
        this.IMG_porcentaje = IMG_porcentaje;
    }

    public String getRBC() {
        return RBC;
    }

    public void setRBC(String RBC) {
        this.RBC = RBC;
    }

    public String getHGB() {
        return HGB;
    }

    public void setHGB(String HGB) {
        this.HGB = HGB;
    }

    public String getHCT() {
        return HCT;
    }

    public void setHCT(String HCT) {
        this.HCT = HCT;
    }

    public String getMCV() {
        return MCV;
    }

    public void setMCV(String MCV) {
        this.MCV = MCV;
    }

    public String getMCH() {
        return MCH;
    }

    public void setMCH(String MCH) {
        this.MCH = MCH;
    }

    public String getMCHC() {
        return MCHC;
    }

    public void setMCHC(String MCHC) {
        this.MCHC = MCHC;
    }

    public String getRDW_CV() {
        return RDW_CV;
    }

    public void setRDW_CV(String RDW_CV) {
        this.RDW_CV = RDW_CV;
    }

    public String getRDW_SD() {
        return RDW_SD;
    }

    public void setRDW_SD(String RDW_SD) {
        this.RDW_SD = RDW_SD;
    }

    public String getPLT() {
        return PLT;
    }

    public void setPLT(String PLT) {
        this.PLT = PLT;
    }

    public String getMPV() {
        return MPV;
    }

    public void setMPV(String MPV) {
        this.MPV = MPV;
    }

    public String getPDW() {
        return PDW;
    }

    public void setPDW(String PDW) {
        this.PDW = PDW;
    }

    public String getPCT() {
        return PCT;
    }

    public void setPCT(String PCT) {
        this.PCT = PCT;
    }

    public String getP_LCC() {
        return P_LCC;
    }

    public void setP_LCC(String p_LCC) {
        P_LCC = p_LCC;
    }

    public String getP_LCR() {
        return P_LCR;
    }

    public void setP_LCR(String p_LCR) {
        P_LCR = p_LCR;
    }
    public String getNRBC_numeral() {
        return NRBC_numeral;
    }

    public void setNRBC_numeral(String NRBC_numeral) {
        this.NRBC_numeral = NRBC_numeral;
    }

    public String getNRBC_porcentaje() {
        return NRBC_porcentaje;
    }

    public void setNRBC_porcentaje(String NRBC_porcentaje) {
        this.NRBC_porcentaje = NRBC_porcentaje;
    }

    public String getWBC_BF() {
        return WBC_BF;
    }

    public void setWBC_BF(String WBC_BF) {
        this.WBC_BF = WBC_BF;
    }

    public String getTC_BF_numeral() {
        return TC_BF_numeral;
    }

    public void setTC_BF_numeral(String TC_BF_numeral) {
        this.TC_BF_numeral = TC_BF_numeral;
    }
    public String getMN_numeral() {
        return MN_numeral;
    }

    public void setMN_numeral(String MN_numeral) {
        this.MN_numeral = MN_numeral;
    }
    public String getMN_porcentaje() {
        return MN_porcentaje;
    }

    public void setMN_porcentaje(String MN_porcentaje) {
        this.MN_porcentaje = MN_porcentaje;
    }
    public String getPMN_numeral() {
        return PMN_numeral;
    }

    public void setPMN_numeral(String PMN_numeral) {
        this.PMN_numeral = PMN_numeral;
    }
    public String getPMN_porcentaje() {
        return PMN_porcentaje;
    }

    public void setPMN_porcentaje(String PMN_porcentaje) {
        this.PMN_porcentaje = PMN_porcentaje;
    }
    public String getRBC_BF() {
        return RBC_BF;
    }

    public void setRBC_BF(String RBC_BF) {
        this.RBC_BF = RBC_BF;
    }

    public String getID_pacient() {
        return ID_pacient;
    }

    public void setID_pacient(String ID_pacient) {
        this.ID_pacient = ID_pacient;
    }
    public String getNomb() {
        return Nomb;
    }

    public void setNomb(String nomb) {
        Nomb = nomb;
    }
    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }
    public String getFecha_nacim() {
        return Fecha_nacim;
    }

    public void setFecha_nacim(String fecha_nacim) {
        Fecha_nacim = fecha_nacim;
    }
    public String getEd() {
        return Ed;
    }

    public void setEd(String ed) {
        Ed = ed;
    }
    public String getTipo_pac() {
        return Tipo_pac;
    }

    public void setTipo_pac(String tipo_pac) {
        Tipo_pac = tipo_pac;
    }
    public String getGrupo_ref() {
        return Grupo_ref;
    }

    public void setGrupo_ref(String grupo_ref) {
        Grupo_ref = grupo_ref;
    }
    public String getDpto() {
        return Dpto;
    }

    public void setDpto(String dpto) {
        Dpto = dpto;
    }
    public String getN_cama() {
        return N_cama;
    }

    public void setN_cama(String n_cama) {
        N_cama = n_cama;
    }
    public String getFech_extr() {
        return Fech_extr;
    }

    public void setFech_extr(String fech_extr) {
        Fech_extr = fech_extr;
    }
    public String getHora_extr() {
        return Hora_extr;
    }

    public void setHora_extr(String hora_extr) {
        Hora_extr = hora_extr;
    }
    public String getFecha_ext() {
        return Fecha_ext;
    }

    public void setFecha_ext(String fecha_ext) {
        Fecha_ext = fecha_ext;
    }
    public String getHora_entrega() {
        return Hora_entrega;
    }

    public void setHora_entrega(String hora_entrega) {
        Hora_entrega = hora_entrega;
    }
    public String getMedico() {
        return Medico;
    }

    public void setMedico(String medico) {
        Medico = medico;
    }
    public String getOperador() {
        return Operador;
    }

    public void setOperador(String operador) {
        Operador = operador;
    }
    public String getValidado_por() {
        return Validado_por;
    }

    public void setValidado_por(String validado_por) {
        Validado_por = validado_por;
    }
    public String getComents() {
        return Coments;
    }

    public void setComents(String coments) {
        Coments = coments;
    }
    public String getMensaje_WBC() {
        return Mensaje_WBC;
    }

    public void setMensaje_WBC(String mensaje_WBC) {
        Mensaje_WBC = mensaje_WBC;
    }
    public String getMensaje_RBC() {
        return Mensaje_RBC;
    }

    public void setMensaje_RBC(String mensaje_RBC) {
        Mensaje_RBC = mensaje_RBC;
    }
    public String getMensaje_PLT() {
        return Mensaje_PLT;
    }

    public void setMensaje_PLT(String mensaje_PLT) {
        Mensaje_PLT = mensaje_PLT;
    }
    public String getPLT_I() {
        return PLT_I;
    }

    public void setPLT_I(String PLT_I) {
        this.PLT_I = PLT_I;
    }
    public String getWBC_D() {
        return WBC_D;
    }

    public void setWBC_D(String WBC_D) {
        this.WBC_D = WBC_D;
    }
    public String getTNC_D() {
        return TNC_D;
    }

    public void setTNC_D(String TNC_D) {
        this.TNC_D = TNC_D;
    }
    public String getWBC_N() {
        return WBC_N;
    }

    public void setWBC_N(String WBC_N) {
        this.WBC_N = WBC_N;
    }
    public String getTNC_N() {
        return TNC_N;
    }

    public void setTNC_N(String TNC_N) {
        this.TNC_N = TNC_N;
    }
    public String getHFC_numeral() {
        return HFC_numeral;
    }

    public void setHFC_numeral(String HFC_numeral) {
        this.HFC_numeral = HFC_numeral;
    }
    public String getHFC_porcentaje() {
        return HFC_porcentaje;
    }

    public void setHFC_porcentaje(String HFC_porcentaje) {
        this.HFC_porcentaje = HFC_porcentaje;
    }
    public String getIME_porcentaje() {
        return IME_porcentaje;
    }

    public void setIME_porcentaje(String ME_porcentaje) {
        this.IME_porcentaje = IME_porcentaje;
    }
    public String getIME_numeral() {
        return IME_numeral;
    }

    public void setIME_numeral(String IME_numeral) {
        this.IME_numeral = IME_numeral;
    }
    public String getH_NR_porcentaje() {
        return H_NR_porcentaje;
    }

    public void setH_NR_porcentaje(String h_NR_porcentaje) {
        H_NR_porcentaje = h_NR_porcentaje;
    }
    public String getL_NR_porcentaje() {
        return L_NR_porcentaje;
    }

    public void setL_NR_porcentaje(String l_NR_porcentaje) {
        L_NR_porcentaje = l_NR_porcentaje;
    }
    public String getNLR() {
        return NLR;
    }

    public void setNLR(String NLR) {
        this.NLR = NLR;
    }
    public String getPLR() {
        return PLR;
    }

    public void setPLR(String PLR) {
        this.PLR = PLR;
    }
    public String getInR_numeral() {
        return InR_numeral;
    }

    public void setInR_numeral(String inR_numeral) {
        InR_numeral = inR_numeral;
    }
    public String getInR_porcentaje() {
        return InR_porcentaje;
    }

    public void setInR_porcentaje(String inR_porcentaje) {
        InR_porcentaje = inR_porcentaje;
    }
    public String getMicro_numeral() {
        return Micro_numeral;
    }

    public void setMicro_numeral(String micro_numeral) {
        Micro_numeral = micro_numeral;
    }
    public String getMicro_porcentaje() {
        return Micro_porcentaje;
    }

    public void setMicro_porcentaje(String micro_porcentaje) {
        Micro_porcentaje = micro_porcentaje;
    }
    public String getMacro_numeral() {
        return Macro_numeral;
    }

    public void setMacro_numeral(String macro_numeral) {
        Macro_numeral = macro_numeral;
    }
    public String getMacro_porcentaje() {
        return Macro_porcentaje;
    }

    public void setMacro_porcentaje(String macro_porcentaje) {
        Macro_porcentaje = macro_porcentaje;
    }
    public String getPDW_SD() {
        return PDW_SD;
    }

    public void setPDW_SD(String PDW_SD) {
        this.PDW_SD = PDW_SD;
    }
    public String getEos_BF_numeral() {
        return Eos_BF_numeral;
    }

    public void setEos_BF_numeral(String eos_BF_numeral) {
        Eos_BF_numeral = eos_BF_numeral;
    }
    public String getEos_BF_porcentaje() {
        return Eos_BF_porcentaje;
    }

    public void setEos_BF_porcentaje(String eos_BF_porcentaje) {
        Eos_BF_porcentaje = eos_BF_porcentaje;
    }
    public String getNeu_BF_numeral() {
        return Neu_BF_numeral;
    }

    public void setNeu_BF_numeral(String neu_BF_numeral) {
        Neu_BF_numeral = neu_BF_numeral;
    }
    public String getNeu_BF_porcentaje() {
        return Neu_BF_porcentaje;
    }

    public void setNeu_BF_porcentaje(String neu_BF_porcentaje) {
        Neu_BF_porcentaje = neu_BF_porcentaje;
    }
    public String getLY_BF_numeral() {
        return LY_BF_numeral;
    }

    public void setLY_BF_numeral(String LY_BF_numeral) {
        this.LY_BF_numeral = LY_BF_numeral;
    }
    public String getLY_BF_porcentaje() {
        return LY_BF_porcentaje;
    }

    public void setLY_BF_porcentaje(String LY_BF_porcentaje) {
        this.LY_BF_porcentaje = LY_BF_porcentaje;
    }
    public String getMO_BF_numeral() {
        return MO_BF_numeral;
    }

    public void setMO_BF_numeral(String MO_BF_numeral) {
        this.MO_BF_numeral = MO_BF_numeral;
    }
    public String getMO_BF_porcentaje() {
        return MO_BF_porcentaje;
    }
    public void setMO_BF_porcentaje(String MO_BF_porcentaje) {
        this.MO_BF_porcentaje = MO_BF_porcentaje;
    }
    public String getHF_BF_numeral() {
        return HF_BF_numeral;
    }

    public void setHF_BF_numeral(String HF_BF_numeral) {
        this.HF_BF_numeral = HF_BF_numeral;
    }
    public String getHF_BF_porcentaje() {
        return HF_BF_porcentaje;
    }

    public void setHF_BF_porcentaje(String HF_BF_porcentaje) {
        this.HF_BF_porcentaje = HF_BF_porcentaje;
    }
    public String getRBC_BF_I() {
        return RBC_BF_I;
    }

    public void setRBC_BF_I(String RBC_BF_I) {
        this.RBC_BF_I = RBC_BF_I;
    }
    public String getGrupo_sang() {
        return Grupo_sang;
    }

    public void setGrupo_sang(String grupo_sang) {
        Grupo_sang = grupo_sang;
    }
    public String getESR() {
        return ESR;
    }

    public void setESR(String ESR) {
        this.ESR = ESR;
    }
    public String getParam_microscop() {
        return Param_microscop;
    }

    public void setParam_microscop(String param_microscop) {
        Param_microscop = param_microscop;
    }

   /* --------- Nuevos campos agregados --------- */
    public String getLYM_porcentaje() {
        return LYM_porcentaje;
    }

    public void setLYM_porcentaje(String LYM_porcentaje) {
        this.LYM_porcentaje = LYM_porcentaje;
    }

    public String getLYM_numeral() {
        return LYM_numeral;
    }

    public void setLYM_numeral(String LYM_numeral) {
        this.LYM_numeral = LYM_numeral;
    }

    public String getPLC_C() {
        return PLC_C;
    }

    public void setPLC_C(String PLC_C) {
        this.PLC_C = PLC_C;
    }

    public String getHNR_porcentaje() {
        return HNR_porcentaje;
    }

    public void setHNR_porcentaje(String HNR_porcentaje) {
        this.HNR_porcentaje = HNR_porcentaje;
    }

    public String getLNR_porcentaje() {
        return LNR_porcentaje;
    }

    public void setLNR_porcentaje(String LNR_porcentaje) {
        this.LNR_porcentaje = LNR_porcentaje;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        this.Mensaje = mensaje;
    }

    public String getNombre_equipo() {
        return Nombre_equipo;
    }

    public void setNombre_equipo(String nombre_equipo) {
        Nombre_equipo = nombre_equipo;
    }

    public Date getFec() {
        return fec;
    }

    public void setFec(Date fec) {
        this.fec = fec;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getValido() {
        return valido;
    }

    public void setValido(String valido) {
        this.valido = valido;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public boolean isProcesoAutomatico() {
        return procesoAutomatico;
    }

    public void setProcesoAutomatico(boolean procesoAutomatico) {
        this.procesoAutomatico = procesoAutomatico;
    }

    public int getID_CONSECUTIVO() {
        return ID_CONSECUTIVO;
    }

    public void setID_CONSECUTIVO(int ID_CONSECUTIVO) {
        this.ID_CONSECUTIVO = ID_CONSECUTIVO;
    }

    public String getFechaPa() {
        return fechaPa;
    }

    public void setFechaPa(String fechaPa) {
        this.fechaPa = fechaPa;
    }
}
