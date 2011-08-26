describe('frontlinesms.log', function () {
    it("should allow logging without evil failures like when console.log is unavailable", function() {
       frontlinesms.log("You should see this in the console if your browser supports it");
       expect("succeeded without exception").toBeTruthy();
    });
});