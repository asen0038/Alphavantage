package alphavantage.model;

import alphavantage.company.Company;
import alphavantage.company.CompanyBuilder;
import alphavantage.company.CompanyImpl;
import alphavantage.company.information.*;
import alphavantage.message.Message;
import alphavantage.query.Database;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AlphavantageFacadeTest {

    @Test(expected = IllegalArgumentException.class)
    public void facadeInvalidSymbolTest1() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        int n = af.receive(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void facadeInvalidSymbolTest2() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        int n = af.receive("");
    }

    @Test
    public void facadeRecieveFromAPINew() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        List<String> valueOverview = new ArrayList<>();
        Overview o = new OverviewImpl(valueOverview);
        List<AnnualReport> reports = new ArrayList<>();
        List<String> valueReports = new ArrayList<>();
        AnnualReport an = new AnnualReport(valueReports);
        reports.add(an);
        CashFlow cf = new CashFlowImpl(reports);
        Company c = new CompanyImpl(o, cf);
        CompanyBuilder cb = mock(CompanyBuilder.class);
        Database d = mock(Database.class);
        when(cb.generateData(anyString(), anyString())).thenReturn(c);
        when(d.checkIfExists(anyString())).thenReturn(0);
        af.setCompanyBuilder(cb);
        af.setDatabase(d);
        af.setSplitYear("1999");
        assertFalse(c.isRecentlySplit());
        int n = af.receive("IBM");
        assertEquals(n, 1);
        verify(cb).generateData(anyString(), anyString());
        verify(d, times(2)).connect();
        verify(d, times(2)).disconnect();
        verify(d).checkIfExists(anyString());
        verify(d).insertData(anyList());
    }

    @Test
    public void facadeRecieveFromAPINewDummy() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("offline", "online");
        List<String> valueOverview = new ArrayList<>();
        Overview o = new OverviewImpl(valueOverview);
        List<AnnualReport> reports = new ArrayList<>();
        List<String> valueReports = new ArrayList<>();
        AnnualReport an = new AnnualReport(valueReports);
        reports.add(an);
        CashFlow cf = new CashFlowImpl(reports);
        Company c = new CompanyImpl(o, cf);
        CompanyBuilder cb = mock(CompanyBuilder.class);
        Database d = mock(Database.class);
        when(cb.generateData(anyString(), anyString())).thenReturn(c);
        when(d.checkIfExists(anyString())).thenReturn(0);
        af.setCompanyBuilder(cb);
        af.setDatabase(d);
        af.setSplitYear("1999");
        assertFalse(c.isRecentlySplit());
        int n = af.receive("IBM");
        assertEquals(n, 1);
        verify(cb).generateData(anyString(), anyString());
        verify(d, times(2)).connect();
        verify(d, times(2)).disconnect();
        verify(d).checkIfExists(anyString());
        verify(d).insertData(anyList());
    }

    @Test
    public void facadeRecieveFromDB() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        List<String> valueOverview = new ArrayList<>();
        Overview o = new OverviewImpl(valueOverview);
        List<AnnualReport> reports = new ArrayList<>();
        List<String> valueReports = new ArrayList<>();
        AnnualReport an = new AnnualReport(valueReports);
        reports.add(an);
        CashFlow cf = new CashFlowImpl(reports);
        Company c = new CompanyImpl(o, cf);
        CompanyBuilder cb = mock(CompanyBuilder.class);
        Database d = mock(Database.class);
        when(cb.buildFromDB(anyList())).thenReturn(c);
        when(d.checkIfExists(anyString())).thenReturn(1);
        List<String> data = new ArrayList<>();
        when(d.selectData(anyString())).thenReturn(data);
        af.setCompanyBuilder(cb);
        af.setDatabase(d);
        af.setSplitYear("1999");
        assertFalse(c.isRecentlySplit());
        int n = af.receive("IBM");
        assertEquals(n, 0);
        String res = af.receiveDB("IBM");
        assertNotNull(res);
        verify(cb).buildFromDB(anyList());
        verify(d, times(2)).connect();
        verify(d, times(2)).disconnect();
        verify(d).checkIfExists(anyString());
        verify(d).selectData(anyString());
    }

    @Test
    public void facadeRecieveFromDBDummy() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("offline", "online");
        List<String> valueOverview = new ArrayList<>();
        Overview o = new OverviewImpl(valueOverview);
        List<AnnualReport> reports = new ArrayList<>();
        List<String> valueReports = new ArrayList<>();
        AnnualReport an = new AnnualReport(valueReports);
        reports.add(an);
        CashFlow cf = new CashFlowImpl(reports);
        Company c = new CompanyImpl(o, cf);
        CompanyBuilder cb = mock(CompanyBuilder.class);
        Database d = mock(Database.class);
        when(cb.buildFromDB(anyList())).thenReturn(c);
        when(d.checkIfExists(anyString())).thenReturn(1);
        List<String> data = new ArrayList<>();
        when(d.selectData(anyString())).thenReturn(data);
        af.setCompanyBuilder(cb);
        af.setDatabase(d);
        af.setSplitYear("1999");
        assertFalse(c.isRecentlySplit());
        int n = af.receive("IBM");
        assertEquals(n, 0);
        String res = af.receiveDB("IBM");
        assertNotNull(res);
        verify(cb).buildFromDB(anyList());
        verify(d, times(2)).connect();
        verify(d, times(2)).disconnect();
        verify(d).checkIfExists(anyString());
        verify(d).selectData(anyString());
    }

    @Test
    public void facadeRecieveFromAPIChoice() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        List<String> valueOverview = new ArrayList<>();
        Overview o = new OverviewImpl(valueOverview);
        List<AnnualReport> reports = new ArrayList<>();
        List<String> valueReports = new ArrayList<>();
        AnnualReport an = new AnnualReport(valueReports);
        reports.add(an);
        CashFlow cf = new CashFlowImpl(reports);
        Company c = new CompanyImpl(o, cf);
        CompanyBuilder cb = mock(CompanyBuilder.class);
        Database d = mock(Database.class);
        when(cb.generateData(anyString(), anyString())).thenReturn(c);
        when(d.checkIfExists(anyString())).thenReturn(1);
        af.setCompanyBuilder(cb);
        af.setDatabase(d);
        af.setSplitYear("1999");
        assertFalse(c.isRecentlySplit());
        int n = af.receive("IBM");
        assertEquals(n, 0);
        af.receiveApi("IBM");
        verify(cb).generateData(anyString(), anyString());
        verify(d, times(2)).connect();
        verify(d, times(2)).disconnect();
        verify(d).checkIfExists(anyString());
        verify(d).updateData(anyList());
    }

    @Test
    public void facadeRecieveFromAPIChoiceDummy() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("offline", "online");
        List<String> valueOverview = new ArrayList<>();
        Overview o = new OverviewImpl(valueOverview);
        List<AnnualReport> reports = new ArrayList<>();
        List<String> valueReports = new ArrayList<>();
        AnnualReport an = new AnnualReport(valueReports);
        reports.add(an);
        CashFlow cf = new CashFlowImpl(reports);
        Company c = new CompanyImpl(o, cf);
        CompanyBuilder cb = mock(CompanyBuilder.class);
        Database d = mock(Database.class);
        when(cb.generateData(anyString(), anyString())).thenReturn(c);
        when(d.checkIfExists(anyString())).thenReturn(1);
        af.setCompanyBuilder(cb);
        af.setDatabase(d);
        af.setSplitYear("1999");
        assertFalse(c.isRecentlySplit());
        int n = af.receive("IBM");
        assertEquals(n, 0);
        af.receiveApi("IBM");
        verify(cb).generateData(anyString(), anyString());
        verify(d, times(2)).connect();
        verify(d, times(2)).disconnect();
        verify(d).checkIfExists(anyString());
        verify(d).updateData(anyList());
    }

    @Test
    public void facadeRecieveAndSendMessage() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        List<String> valueOverview = new ArrayList<>();
        Overview o = new OverviewImpl(valueOverview);
        List<AnnualReport> reports = new ArrayList<>();
        List<String> valueReports = new ArrayList<>();
        AnnualReport an = new AnnualReport(valueReports);
        reports.add(an);
        CashFlow cf = new CashFlowImpl(reports);
        Company c = new CompanyImpl(o, cf);
        CompanyBuilder cb = mock(CompanyBuilder.class);
        Database d = mock(Database.class);
        Message m = mock(Message.class);
        when(cb.generateData(anyString(), anyString())).thenReturn(c);
        when(d.checkIfExists(anyString())).thenReturn(0);
        af.setCompanyBuilder(cb);
        af.setDatabase(d);
        af.setMessager(m);
        af.setSplitYear("1999");
        assertFalse(c.isRecentlySplit());
        int n = af.receive("IBM");
        assertEquals(n, 1);
        af.sendData();
        verify(cb).generateData(anyString(), anyString());
        verify(d, times(2)).connect();
        verify(d, times(2)).disconnect();
        verify(d).checkIfExists(anyString());
        verify(d).insertData(anyList());
        verify(m).setDetails();
        verify(m).configureMessage(eq(c));
        verify(m).sendMessage(anyString());
    }

    @Test
    public void facadeRecieveAndSendMessageDummy() throws SQLException {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "offline");
        List<String> valueOverview = new ArrayList<>();
        Overview o = new OverviewImpl(valueOverview);
        List<AnnualReport> reports = new ArrayList<>();
        List<String> valueReports = new ArrayList<>();
        AnnualReport an = new AnnualReport(valueReports);
        reports.add(an);
        CashFlow cf = new CashFlowImpl(reports);
        Company c = new CompanyImpl(o, cf);
        CompanyBuilder cb = mock(CompanyBuilder.class);
        Database d = mock(Database.class);
        Message m = mock(Message.class);
        when(cb.generateData(anyString(), anyString())).thenReturn(c);
        when(d.checkIfExists(anyString())).thenReturn(0);
        af.setCompanyBuilder(cb);
        af.setDatabase(d);
        af.setMessager(m);
        af.setSplitYear("1999");
        assertFalse(c.isRecentlySplit());
        int n = af.receive("IBM");
        assertEquals(n, 1);
        af.sendData();
        verify(cb).generateData(anyString(), anyString());
        verify(d, times(2)).connect();
        verify(d, times(2)).disconnect();
        verify(d).checkIfExists(anyString());
        verify(d).insertData(anyList());
        verify(m).setDetails();
        verify(m).configureMessage(eq(c));
        verify(m).sendMessage(anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void facadeInvalidYearTest1() {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        af.setSplitYear(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void facadeInvalidYearTest2() {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        af.setSplitYear("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void facadeInvalidYearTest3() {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        af.setSplitYear("text");
    }

    @Test(expected = IllegalStateException.class)
    public void facadeInvalidYearTest4() {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        af.setSplitYear("1899");
    }

    @Test(expected = IllegalStateException.class)
    public void facadeInvalidYearTest5() {
        AlphaVantageFacade af = new AlphaVantageFacadeImpl("online", "online");
        af.setSplitYear("2022");
    }

}
