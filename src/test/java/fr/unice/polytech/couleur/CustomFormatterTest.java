package fr.unice.polytech.couleur;

import fr.unice.polytech.MoteurDeJeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomFormatterTest {
    CustomFormatter customFormatter;
    String messageDefault;
    String dateString;
    Date date;
    LogRecord OFFLogRecord;
    LogRecord SEVERELogRecord;
    LogRecord WARNINGLogRecord;
    LogRecord INFOLogRecord;
    LogRecord CONFIGLogRecord;
    LogRecord FINELogRecord;
    LogRecord FINERLogRecord;
    LogRecord FINESTLogRecord;
    LogRecord ALLLogRecord;

    @BeforeEach
    void setup() {
        this.customFormatter = new CustomFormatter();
        this.messageDefault = "Message de Test par default";
        this.OFFLogRecord = new LogRecord(Level.OFF, this.messageDefault);
        this.SEVERELogRecord = new LogRecord(Level.SEVERE, this.messageDefault);
        this.WARNINGLogRecord = new LogRecord(Level.WARNING, this.messageDefault);
        this.INFOLogRecord = new LogRecord(Level.INFO, this.messageDefault);
        this.CONFIGLogRecord = new LogRecord(Level.CONFIG, this.messageDefault);
        this.FINELogRecord = new LogRecord(Level.FINE, this.messageDefault);
        this.FINERLogRecord = new LogRecord(Level.FINER, this.messageDefault);
        this.FINESTLogRecord = new LogRecord(Level.FINEST, this.messageDefault);
        this.ALLLogRecord = new LogRecord(Level.ALL, this.messageDefault);
        this.date = new Date();
    }

    void updateDate() {
        this.dateString = "[" + new SimpleDateFormat("dd/MM/yyyy").format(date) + " " + new SimpleDateFormat("HH:mm:ss").format(date) + "]";
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void formatOFF() {
        this.date.setTime(OFFLogRecord.getMillis());
        this.updateDate();
        assertEquals(String.format(CouleurConsole.RED + "%1$s" + CouleurConsole.BLACK_BRIGHT + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + OFFLogRecord.getLevel().getLocalizedName() + "]", this.customFormatter.formatMessage(OFFLogRecord)), this.customFormatter.format(OFFLogRecord));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void formatSEVERE() {
        this.date.setTime(SEVERELogRecord.getMillis());
        this.updateDate();
        assertEquals(String.format(CouleurConsole.RED + "%1$s" + CouleurConsole.RED_BRIGHT + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + SEVERELogRecord.getLevel().getLocalizedName() + "]", this.customFormatter.formatMessage(SEVERELogRecord)), this.customFormatter.format(SEVERELogRecord));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void formatWARNING() {
        this.date.setTime(WARNINGLogRecord.getMillis());
        this.updateDate();
        assertEquals(String.format(CouleurConsole.RED + "%1$s" + CouleurConsole.RED + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + WARNINGLogRecord.getLevel().getLocalizedName() + "]", this.customFormatter.formatMessage(WARNINGLogRecord)), this.customFormatter.format(WARNINGLogRecord));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void formatINFO() {
        this.date.setTime(INFOLogRecord.getMillis());
        this.updateDate();
        assertEquals(String.format(CouleurConsole.RED + "%1$s" + CouleurConsole.YELLOW_BRIGHT + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + INFOLogRecord.getLevel().getLocalizedName() + "]", this.customFormatter.formatMessage(INFOLogRecord)), this.customFormatter.format(INFOLogRecord));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void formatCONFIG() {
        this.date.setTime(CONFIGLogRecord.getMillis());
        this.updateDate();
        assertEquals(String.format(CouleurConsole.RED + "%1$s" + CouleurConsole.YELLOW + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + CONFIGLogRecord.getLevel().getLocalizedName() + "]", this.customFormatter.formatMessage(CONFIGLogRecord)), this.customFormatter.format(CONFIGLogRecord));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void formatFINE() {
        this.date.setTime(FINELogRecord.getMillis());
        this.updateDate();
        assertEquals(String.format(CouleurConsole.RED + "%1$s" + CouleurConsole.BLUE + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + FINELogRecord.getLevel().getLocalizedName() + "]", this.customFormatter.formatMessage(FINELogRecord)), this.customFormatter.format(FINELogRecord));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void formatFINER() {
        this.date.setTime(FINERLogRecord.getMillis());
        this.updateDate();
        assertEquals(String.format(CouleurConsole.RED + "%1$s" + CouleurConsole.GREEN_BRIGHT + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + FINERLogRecord.getLevel().getLocalizedName() + "]", this.customFormatter.formatMessage(FINERLogRecord)), this.customFormatter.format(FINERLogRecord));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void formatFINEST() {
        this.date.setTime(FINESTLogRecord.getMillis());
        this.updateDate();
        assertEquals(String.format(CouleurConsole.RED + "%1$s" + CouleurConsole.GREEN + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + FINESTLogRecord.getLevel().getLocalizedName() + "]", this.customFormatter.formatMessage(FINESTLogRecord)), this.customFormatter.format(FINESTLogRecord));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void formatALL() {
        this.date.setTime(ALLLogRecord.getMillis());
        this.updateDate();
        assertEquals(String.format(CouleurConsole.RED + "%1$s" + CouleurConsole.PURPLE_BRIGHT + "%2$8s" + CouleurConsole.RESET + "   %3$s%n", dateString, "[" + ALLLogRecord.getLevel().getLocalizedName() + "]", this.customFormatter.formatMessage(ALLLogRecord)), this.customFormatter.format(ALLLogRecord));
    }
}